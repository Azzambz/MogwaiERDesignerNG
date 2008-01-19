/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.erdesignerng.plugins.squirrel.action;

import java.awt.Point;
import java.awt.event.ActionEvent;

import de.erdesignerng.plugins.squirrel.SquirrelMogwaiController;
import de.erdesignerng.plugins.squirrel.SquirrelMogwaiPlugin;
import de.erdesignerng.plugins.squirrel.SquirrelMogwaiPluginResources;

import net.sourceforge.squirrel_sql.client.IApplication;
import net.sourceforge.squirrel_sql.client.action.SquirrelAction;
import net.sourceforge.squirrel_sql.client.session.ISession;
import net.sourceforge.squirrel_sql.client.session.action.ISessionAction;
import net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.ObjectTreeNode;
import net.sourceforge.squirrel_sql.fw.sql.DatabaseObjectType;

/**
 * @author $Author: mirkosertic $
 * @version $Date: 2008-01-18 21:05:36 $
 */
public class StartMogwaiAction extends SquirrelAction implements ISessionAction {

    protected ISession session;

    protected final SquirrelMogwaiPlugin plugin;

    public StartMogwaiAction(IApplication aApplication, SquirrelMogwaiPluginResources aResources, SquirrelMogwaiPlugin aPlugin) {
        super(aApplication, aResources);
        plugin = aPlugin;
    }

    public void actionPerformed(ActionEvent evt) {
        if (session != null) {
            ObjectTreeNode[] selectedNodes = session.getSessionSheet().getObjectTreePanel().getSelectedNodes();

            Point[] refCascadeIndent = new Point[1];
            SquirrelMogwaiController toAddTo = null;

            for (int i = 0; i < selectedNodes.length; i++) {
                if (selectedNodes[i].getDatabaseObjectType() == DatabaseObjectType.TABLE) {

                    SquirrelMogwaiController[] controllers = plugin.getGraphControllers(session);
                    if (0 == controllers.length) {
                        toAddTo = plugin.createNewGraphControllerForSession(session);
                    }
                }

                // Handle whatever
                // toAddTo.addTable(selectedNodes[i], refCascadeIndent);
            }

        }
    }

    public void setSession(ISession aSession) {
        session = aSession;
    }
}