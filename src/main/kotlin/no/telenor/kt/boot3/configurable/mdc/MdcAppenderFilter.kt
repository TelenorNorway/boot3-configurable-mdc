package no.telenor.kt.boot3.configurable.mdc

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import no.telenor.kt.MDCTransaction
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcAppenderFilter(
	private val keyRegistry: KeyRegistry,
	// ensure that this filter is loaded after the request context
	// filter
	@Suppress("UNUSED_PARAMETER") `_`: MdcRequestContextFilter,
) : OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		val context = MdcRequestContext.from(request)

		context ?: return filterChain.doFilter(request, response)

		val snapshot = MDCTransaction.builder().let {
			for ((key, value) in keyRegistry.keys) {
				if (!value.enabled) continue
				it.putIfNotNull(key, value.valueCollector(context))
			}
			it
		}.commit()

		try {
			filterChain.doFilter(request, response)
		} finally {
			snapshot.restore()
		}
	}
}
