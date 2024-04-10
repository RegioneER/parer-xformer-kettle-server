/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.parer.kettle.server;

import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class KettleServerBanner implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println("================================================================");
        out.println("(`-')                              (`-') <-. (`-')   (`-')  _   (`-')");
        out.println("(OO )_.->    <-.          .->   <-.(OO )    \\(OO )_  ( OO).-/<-.(OO )");
        out.println("(_| \\_)--.(`-')-----.(`-')----. ,------,),--./  ,-.)(,------.,------,)");
        out.println("\\  `.'  / (OO|(_\\---'( OO).-.  '|   /`. '|   `.'   | |  .---'|   /`. '");
        out.println("\\    .')  / |  '--. ( _) | |  ||  |_.' ||  |'.'|  |(|  '--. |  |_.' |");
        out.println(".'    \\   \\_)  .--'  \\|  |)|  ||  .   .'|  |   |  | |  .--' |  .   .'");
        out.println("/  .'.  \\   `|  |_)    '  '-'  '|  |\\  \\ |  |   |  | |  `---.|  |\\  \\ ");
        out.println("`--'   '--'   `--'       `-----' `--' '--'`--'   `--' `------'`--' '--'");
        out.println("================================================================");

    }
}
