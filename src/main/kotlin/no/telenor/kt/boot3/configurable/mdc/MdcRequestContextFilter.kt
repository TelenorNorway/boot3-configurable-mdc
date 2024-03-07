package no.telenor.kt.boot3.configurable.mdc

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.BeanFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcRequestContextFilter(private val beanFactory: BeanFactory) : OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		MdcRequestContext.push(request, beanFactory)
		try {
			filterChain.doFilter(request, response)
		} finally {
			MdcRequestContext.pop(request)
		}
	}
}
