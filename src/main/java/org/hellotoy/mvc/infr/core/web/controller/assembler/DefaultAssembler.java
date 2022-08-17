package org.hellotoy.mvc.infr.core.web.controller.assembler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
/**
 * 通用泛型组装器
 * @author admin
 *
 * @param <I>
 * @param <O>
 * @param <M>
 */
@Slf4j
public class DefaultAssembler<I, O, M> implements Assembler<I, O, M>{

	protected final Class<I> inputClass;

	protected final Class<O> outputClass;

	protected final Class<M> modelClass;

	@SuppressWarnings("unchecked")
	public DefaultAssembler() {
		inputClass = (Class<I>) getGenericClass(0);
		outputClass = (Class<O>) getGenericClass(1);
		modelClass = (Class<M>) getGenericClass(2);
		Assert.notNull(inputClass, "inputClass Class is null");
		Assert.notNull(outputClass, "outputClass Class is null");
		Assert.notNull(modelClass, "modelClass Class is null");
	}

	/**
	 * 获取泛型
	 * 
	 * @param index
	 * @return
	 */
	private Class<?> getGenericClass(int index) {
		return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}

	/**
	 * 组装模型
	 * 
	 * @param param
	 * @return
	 */
	public M assemblerModel(I param) {
		if (param == null) {
			return null;
		}
		M model = BeanUtils.instantiateClass(modelClass);
		complexCopyBean(param, model);
		return model;
	}

	/**
	 * 组装返回值
	 * 
	 * @param model
	 * @return
	 */
	public O assemblerInfo(M model) {
		if (model == null) {
			return null;
		}
		O out = BeanUtils.instantiateClass(outputClass);
		complexCopyBean(model, out);
		return out;
	}

	private void complexCopyBean(Object source, Object target) {
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							if (ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
									readMethod.getReturnType())) {
								writeMethod.invoke(target, value);
							} else if (writeMethod.getParameterTypes()[0].isEnum()
									|| readMethod.getReturnType().isEnum()) {
								value = convertEnum(value, writeMethod.getParameterTypes()[0]);
								writeMethod.invoke(target, value);
							}
						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}

	private Object convertEnum(Object value, Method readMethod, Method writeMethod) {
		Object retData = value;
		try {
			if (readMethod.getReturnType().isEnum()) {
				if (writeMethod.getParameterTypes()[0].isEnum()) {
					// 1 直接对拷
					retData = convertEnum(value, writeMethod.getParameterTypes()[0]);
					// 根据共同的属性拷贝
				} else {
					//String
					if (writeMethod.getParameterTypes()[0] == String.class) {
						retData = value.toString();
					}
					//
				}
				//根据共同的属性拷贝
			} else {
				// writeMethod is Enum but readMethod is not
				if (readMethod.getReturnType() == String.class) {
					Object[] objs = writeMethod.getParameterTypes()[0].getEnumConstants();
					for(Object obj:objs) {
						if(obj.toString().equals(value.toString())) {
							retData =obj;
						}
					}
				}
			}
		} catch (Exception e) {
			log.warn("auto copy field occured error!",e);
		}
		
		return retData;
	}

	/**
	 * 转换枚举类型
	 * 
	 * @param <enumFrom>
	 * @param <enumTo>
	 * @param from
	 * @param to
	 * @return
	 */
	private static <enumFrom, enumTo> enumTo convertEnum(enumFrom from, Class<enumTo> to) {
		enumTo rReturn = null;
		if (to.isEnum()) {
			enumTo[] array = to.getEnumConstants();
			for (enumTo enu : array) {
				if (enu.toString().equals(from.toString())) {
					rReturn = enu;
					break;
				}
			}
		}
		return rReturn;
	}
}
