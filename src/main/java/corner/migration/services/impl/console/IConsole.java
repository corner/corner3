/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.migration.services.impl.console;

/**
 * 颜色Console的接口

 * @version $Revision: 2089 $
 * @since 0.9.0
 */
public interface IConsole {

   /**
    * Set console foreground color.
    * 
    * @param   color   Set this color.
    */
   public void setForegroundColor(ConsoleForegroundColor color);

   /**
    * Set console background color.
    * 
    * @param   color   Set this color.
    */
   public void setBackgroundColor(ConsoleBackgroundColor color);

   /**
    * Reset console backgound and foreground colors to initial values.
    */
   public void resetColors();

}