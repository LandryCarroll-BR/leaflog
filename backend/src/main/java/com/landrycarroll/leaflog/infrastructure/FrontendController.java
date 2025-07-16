package com.landrycarroll.leaflog.infrastructure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that forwards all non-API and non-static requests to the frontend's index.html.
 * <p>
 * This is typically used in single-page applications (SPAs) like React or Angular,
 * where routing is handled on the client side.
 */
@Controller
public class FrontendController {

    /**
     * Redirects all unmatched routes (excluding API and static resources) to {@code /index.html}.
     *
     * <p>The regular expression excludes paths starting with {@code /api}, {@code /static},
     * and requests for {@code favicon.ico} or {@code index.html} itself.</p>
     *
     * <p>This allows Spring Boot to serve {@code index.html} so that the frontend router can take over.</p>
     *
     * @return a forward directive to {@code /index.html}
     */
    @RequestMapping(value = {"/{path:^(?!api|static|favicon\\.ico|index\\.html).*}", "/"})
    public String redirect() {
        return "forward:/index.html";
    }
}