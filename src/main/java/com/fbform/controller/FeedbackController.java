package com.fbform.controller;

import com.fbform.entity.Feedback;
import com.fbform.repository.FeedbackRepository;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@Scope(value = "session")
@Component(value = "feedbackController")
@ELBeanName(value = "feedbackController")
@Join(path = "/feedback", to = "/feedback-form.jsf")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepo;

    private Feedback feedback = new Feedback();

    public String save() {
        feedbackRepo.save(feedback);
        feedback = new Feedback();
        return "/success.xhtml?faces-redirect=true";
    }

    public Feedback getFeedback() {
        return feedback;
    }


    public void validateMail(ComponentSystemEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        UIInput uiInputMail = (UIInput) components.findComponent("mail");
        String mail = uiInputMail.getLocalValue() == null ? ""
                : uiInputMail.getLocalValue().toString();
        String mailId = uiInputMail.getClientId();

        UIInput uiInputMailRepeat = (UIInput) components.findComponent("mailRepeat");
        String mailRepeat = uiInputMailRepeat.getLocalValue() == null ? ""
                : uiInputMailRepeat.getLocalValue().toString();

        if (mail.isEmpty() || mailRepeat.isEmpty()) {
            return;
        }

        FacesMessage message = null;
        if (!mail.equals(mailRepeat)) {
            message = new FacesMessage("E-mail address must be the same!");
        } else if (!mail.contains("@")) {
            message = new FacesMessage("E-mail address must contain @ symbol!");
        } else if (!mail.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
            message = new FacesMessage("Invalid email address. It must match next form: 'user_name@domain_name' !");
        }

        if (message != null) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(mailId, message);
            context.renderResponse();
        }
    }
}
