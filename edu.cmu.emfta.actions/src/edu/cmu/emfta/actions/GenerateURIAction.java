/**
 * Copyright (c) 2015 Carnegie Mellon University.
 * All Rights Reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITH NO WARRANTIES WHATSOEVER.
 * CARNEGIE MELLON UNIVERSITY EXPRESSLY DISCLAIMS TO THE FULLEST
 * EXTENT PERMITTEDBY LAW ALL EXPRESS, IMPLIED, AND STATUTORY
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND
 * NON-INFRINGEMENT OF PROPRIETARY RIGHTS.

 * This Program is distributed under a BSD license.
 * Please see license.txt file or permission@sei.cmu.edu for more
 * information.
 *
 * DM-0003411
 */


package edu.cmu.emfta.actions;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.action.AbstractExternalJavaAction;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DSemanticDiagramSpec;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class GenerateURIAction extends AbstractExternalJavaAction {

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {

//		System.out.println("[GenerateURIAction] calling execute");
		for (EObject eo : selections) {
			EObject target = null;

//			System.out.println("[GenerateURIAction] eobject = " + eo);

			if (eo instanceof DSemanticDiagramSpec) {
				DSemanticDiagramSpec ds = (DSemanticDiagramSpec) eo;
				target = ds.getTarget();
//
//				System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
//
//				System.out.println("[GenerateURIAction] target = " + target);
			}

			if (eo instanceof DNodeSpec) {
				DNodeSpec ds = (DNodeSpec) eo;
				target = ds.getTarget();
//
//				System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
//
//				System.out.println("[GenerateURIAction] target = " + target);
			}

			if (target != null) {

				if (target instanceof edu.cmu.emfta.Event) {
					generateURI(((edu.cmu.emfta.Event) target));
					return;
				}
			}

			MessageBox dialog = new MessageBox(Display.getDefault().getActiveShell(), SWT.ERROR | SWT.ICON_ERROR);
			dialog.setText("Error");
			dialog.setMessage("Please select an event in the FTA tree");

			dialog.open();

		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {

		/**
		 * For now, we return true all the time. Might need to optimize
		 * it to make it more user-friendly.
		 */
//		System.out.println("[GenerateURIAction] calling canExecute");
		for (EObject eo : selections) {
//			System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());

			if (eo instanceof DSemanticDiagramSpec) {
				DSemanticDiagramSpec ds = (DSemanticDiagramSpec) eo;
				EObject target = ds.getTarget();

//				System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
//
//				System.out.println("[GenerateURIAction] target = " + target);

			}

			if (eo instanceof DNodeSpec) {
				DNodeSpec ds = (DNodeSpec) eo;
				EObject target = ds.getTarget();

//				System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
//
//				System.out.println("[GenerateURIAction] target = " + target);

				if (target instanceof edu.cmu.emfta.Event) {
					return true;
				}

				if (target instanceof edu.cmu.emfta.Tree) {
					return true;
				}

				if (target instanceof edu.cmu.emfta.FTAModel) {
					return true;
				}
			}

		}
		return false;
	}

	public void generateURI(edu.cmu.emfta.Event event) {
//		String fileName;
//		CutSet cs = new CutSet(event);
		GenerateURI genuri = new GenerateURI(event);
		genuri.process();

//		fileName = ResourceUtil.getFile(event.eResource()).getName();
//		fileName = fileName.replace(".emfta", "") + "-cutset.xlsx";
////		System.out.println("filename=" + fileName);
//
////		System.out.println(cs);
//		URI uri = EcoreUtil.getURI(event);
////		System.out.println("directory=" + uri.toPlatformString(true));
////		System.out.println("uri string=" + uri.toString());
//
//		IPath path = new Path(uri.toPlatformString(true));
////
////		System.out.println("path=" + path.makeAbsolute().toOSString());
////		System.out.println("path2=" + Utils.getPath(tree.eResource().getURI()));
//		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
//		String path2 = file.getRawLocation().removeLastSegments(1).toOSString();
//		path2 = path2 + File.separator + fileName;
//		System.out.println("path2=" + path2);
//
////		final InputStream input = new ByteArrayInputStream((cs.toCSV()).getBytes());
//		try {
////			toCreate.create(input, true, null);
//			cs.toWorkbook().write(new FileOutputStream(path2));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Utils.refreshWorkspace(null);
	}
}
