package iomicronaut.hotwired.demo;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.http.annotation.Filter.MATCH_ALL_PATTERN;

@Filter(MATCH_ALL_PATTERN)
public class LogHttpFilter implements HttpServerFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LogHttpFilter.class);
    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        LOG.info("{} {}", request.getMethod(),
                request.getPath());
        for (String name : request.getHeaders().names()) {
            LOG.info("   {}: {}", name, request.getHeaders().get(name));
        }
        return chain.proceed(request);
    }
}
