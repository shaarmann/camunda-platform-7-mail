package org.camunda.bpm.extension.mail.run;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.extension.mail.MailConnectors;
import org.camunda.bpm.extension.mail.config.MailConfiguration;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MailConnectorPlugin extends AbstractProcessEnginePlugin {
  private final Logger LOGGER = Logger.getLogger(this
      .getClass()
      .getName());

  private final MailConfiguration mailConfiguration;

  public MailConnectorPlugin(MailConfiguration mailConfiguration) {this.mailConfiguration = mailConfiguration;}

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    LOGGER.info("Configuring Mail Connectors with properties: ");
    mailConfiguration
        .getProperties()
        .forEach((o, o2) -> LOGGER.info(o + " = " + o2));
    MailConnectors
        .deleteMails()
        .setConfiguration(mailConfiguration);
    MailConnectors
        .pollMails()
        .setConfiguration(mailConfiguration);
    MailConnectors
        .sendMail()
        .setConfiguration(mailConfiguration);
  }
}
