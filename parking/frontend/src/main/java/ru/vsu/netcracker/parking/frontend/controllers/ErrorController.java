package ru.vsu.netcracker.parking.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.vsu.netcracker.parking.frontend.utils.GlobalControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/errors")
public class ErrorController {

    @GetMapping(value = "")
    public ModelAndView handleError(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("404");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 409: {
                errorMsg = "Http Error Code: 409. Conflict";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        errorPage.addObject("mainPageUrl", GlobalControllerAdvice.MAINPAGE);
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
