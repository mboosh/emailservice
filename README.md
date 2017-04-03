### Email Service

Email service which will enable you to send emails through various SMTP servers.

## Build

  * Gradle to handle the build and the library dependencies.

## Front End

  * AngularJS
  * HTML
  * CSS
  
## Back end

 * Java 
 * Servlet 
 
## Library dependencies

  * jackson to read in the JSON object from the HttpServletRequest
  * java mail to handle the sending of the email to smtp servers
  * gson to convert the EmailResponse to a JSON object.
  * log4j
 
## Chain of Responsibility

We use the chain of responsibility pattern to chain a number of smtp server requests together. Each request will try to send the email until one is successfull. If no request is able to send the email then we return an error to the user.

The code is currently only implementing the amazon ses servers but can be expanded to include any smtp server. Just add a new property file in src/main/resources with the following properties:

  * port
  * host
  * userName
  * password
  * fromEmail

Update com.timyelland.emailservice.handler.EmailManager.init(). Add a new handler for the property file and add it to the chain.
