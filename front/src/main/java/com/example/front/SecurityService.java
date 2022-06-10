package com.example.front;



//import com.example.front.view.LoginView;
import com.example.front.view.LoginView;
import com.example.front.view.MainView;
import com.vaadin.flow.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.front.service.VaadinService;
import com.example.front.view.RegistrationView;
import javax.servlet.http.Cookie;

@Component
public class SecurityService implements VaadinServiceInitListener {

    @Autowired
    private VaadinService vaadinService;

    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
                if (cookies != null) {
                    String token = vaadinService.getJWT();
                    if (vaadinService.validate(token).getLogin() == null) {
                        if (enterEvent.getNavigationTarget().equals(RegistrationView.class)) {
                            enterEvent.forwardTo(RegistrationView.class);
                        } else {
                            enterEvent.forwardTo(LoginView.class);
                        }
                    } else if (vaadinService.validate(token).getLogin() != null) {
                        if (enterEvent.getNavigationTarget().equals(RegistrationView.class) ||
                                enterEvent.getNavigationTarget().equals(LoginView.class)) {
                            enterEvent.forwardTo(MainView.class);
                        }
                    }
                }
            });
        });
    }
}
