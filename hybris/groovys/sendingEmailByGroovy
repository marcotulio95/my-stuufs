import de.hybris.platform.commerceservices.model.process.StoreFrontProcessModel;
import java.lang.System;

def currentBaseStore = "arezzo";
def emailService = spring.getBean("emailService");
def baseSiteService = spring.getBean("baseSiteService");
def businessProcessService = spring.getBean("businessProcessService");

def PROCESS = "SedingEmailsProcess-" + currentBaseStore;

def baseSiteModel = baseSiteService.getBaseSiteForUID(currentBaseStore);
def storeFrontProcessModel = (StoreFrontProcessModel) businessProcessService.createProcess(PROCESS+ "-" + System.currentTimeMillis(), PROCESS);
storeFrontProcessModel.setSite(baseSiteModel);

def toAddress = emailService.getOrCreateEmailAddressForEmail("marcotulioarezzo@gmail.com", "Marco Tulio");
def fromAddress = emailService.getOrCreateEmailAddressForEmail("no-reply@gmail.com", "Arezzo Notifications");

def toEmails = [toAddress];
def ccEmails = [];
def bccEmails = [];
def replyTo = "";
def emailSubject = "Testando envio de e-mail por groovy";
def emailBody = "Testano o envio de email por groovy, email body";
def attachments = [];



def emailMessage = emailService.createEmailMessage(toEmails, ccEmails, bccEmails, fromAddress, replyTo,emailSubject, emailBody , attachments );
emailMessage.setProcess(storeFrontProcessModel);

try{
    emailService.send(emailMessage);
    print ("Email enviado com sucesso");
}catch(Exception e){
    e.printStackTrace();
    print("Erro ao enviar o email");
}

