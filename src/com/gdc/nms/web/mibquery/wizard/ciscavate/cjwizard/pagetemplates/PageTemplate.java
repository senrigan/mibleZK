/**
 * Copyright 2008  Eugene Creswick
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gdc.nms.web.mibquery.wizard.ciscavate.cjwizard.pagetemplates;

import javax.swing.JPanel;

import org.zkoss.zul.Panel;

import com.gdc.nms.web.mibquery.wizard.ciscavate.cjwizard.WizardPage;
import com.gdc.nms.web.mibquery.wizard.ciscavate.cjwizard.WizardController;


/**
 * @author rcreswick
 *
 */
public abstract class PageTemplate extends Panel {

   /**
    * Reference to the wizard controller that manages this template.
    */
   private WizardController _controller = null;
   
   /**
    * Called when a new page is to be displayed in the wizard.
    * 
    * @param page The page to display.
    */
   public abstract void setPage(final WizardPage page);

   /**
    * Registers a WizardController with this class.
    * 
    * @param controller
    */
   public void registerController(WizardController controller) {
      _controller = controller;
   }

   /**
    * @return the WizardController that manages this wizard.
    */
   protected WizardController getController() {
      return _controller;
   }
}