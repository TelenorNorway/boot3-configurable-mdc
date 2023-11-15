package no.telenor.kt.boot3.configurable.mdc

import jakarta.servlet.http.HttpServletRequest
import no.telenor.kt.boot3.configurable.mdc.util.getField
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.expression.BeanFactoryResolver
import org.springframework.expression.BeanResolver
import org.springframework.expression.ConstructorResolver
import org.springframework.expression.EvaluationContext
import org.springframework.expression.MethodResolver
import org.springframework.expression.OperatorOverloader
import org.springframework.expression.PropertyAccessor
import org.springframework.expression.TypeComparator
import org.springframework.expression.TypeConverter
import org.springframework.expression.TypeLocator
import org.springframework.expression.TypedValue
import org.springframework.expression.spel.support.StandardEvaluationContext
import java.util.concurrent.ConcurrentHashMap

class MdcRequestContext(beanFactory: BeanFactory) : EvaluationContext, MutableMap<String, Any?> {
	private val ctx = StandardEvaluationContext()
	private val map = getField<MutableMap<String, Any?>>("variables", ctx)

	init {
		val beanFactoryResolver = BeanFactoryResolver(beanFactory)
		ctx.beanResolver = beanFactoryResolver
	}

	override fun getRootObject(): TypedValue = ctx.rootObject
	override fun getPropertyAccessors(): MutableList<PropertyAccessor> = ctx.propertyAccessors
	override fun getConstructorResolvers(): MutableList<ConstructorResolver> = ctx.constructorResolvers
	override fun getMethodResolvers(): MutableList<MethodResolver> = ctx.methodResolvers
	override fun getBeanResolver(): BeanResolver? = ctx.beanResolver
	override fun getTypeLocator(): TypeLocator = ctx.typeLocator
	override fun getTypeConverter(): TypeConverter = ctx.typeConverter
	override fun getTypeComparator(): TypeComparator = ctx.typeComparator
	override fun getOperatorOverloader(): OperatorOverloader = ctx.operatorOverloader
	override fun lookupVariable(name: String): Any? = ctx.lookupVariable(name)
	override fun setVariable(name: String, value: Any?) = ctx.setVariable(name, value)

	override val entries: MutableSet<MutableMap.MutableEntry<String, Any?>> get() = map.entries
	override val keys: MutableSet<String> get() = map.keys
	override val size: Int get() = map.size
	override val values: MutableCollection<Any?> get() = map.values
	override fun isEmpty(): Boolean = map.isEmpty()
	override fun get(key: String): Any? = map[key]
	override fun containsValue(value: Any?): Boolean = map.containsValue(value)
	override fun containsKey(key: String): Boolean = map.containsKey(key)
	override fun putAll(from: Map<out String, Any?>) = map.putAll(from)
	override fun put(key: String, value: Any?): Any? = map.put(key, value)
	override fun remove(key: String): Any? = map.remove(key)
	override fun clear() = map.clear()

	companion object {
		private val current = ConcurrentHashMap<HttpServletRequest, MdcRequestContext>()

		internal fun push(request: HttpServletRequest, beanFactory: BeanFactory) {
			val context = MdcRequestContext(beanFactory)
			context["request"] = request
			current[request] = context
		}

		internal fun pop(request: HttpServletRequest) {
			current.remove(request)
		}

		fun from(request: HttpServletRequest) = current[request]
	}
}
