/*******************************************************************************
 * Copyright (c) 2009 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.completion;

import net.sourceforge.shelled.ui.text.ShellCodeScanner;

import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;
import org.eclipse.dltk.core.ModelException;

public class ShellCompletionEngine extends ScriptCompletionEngine {

	private int actualCompletionPosition;

	@Override
	public void complete(IModuleSource module, int position, int pos) {
		this.actualCompletionPosition = position;
		this.offset = pos;
		for (String keyword : ShellCodeScanner.KEYWORDS) {
			createProposal(keyword, null);
		}
		for (String command : ShellCodeScanner.getCommands()) {
			createProposal(command, null);
		}

		// Completion for model elements.
		try {
			module.getModelElement().accept(new IModelElementVisitor() {
				@Override
				public boolean visit(IModelElement element) {
					if (element.getElementType() > IModelElement.SOURCE_MODULE) {
						createProposal(element.getElementName(), element);
					}
					return true;
				}
			});
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
	}

	private void createProposal(String name, IModelElement element) {
		CompletionProposal proposal = this.createProposal(
				CompletionProposal.KEYWORD, this.actualCompletionPosition);
		proposal.setName(name);
		proposal.setCompletion(name);
		proposal.setReplaceRange(actualCompletionPosition - offset,
				actualCompletionPosition - offset);
		proposal.setRelevance(20);
		proposal.setModelElement(element);
		this.requestor.accept(proposal);
	}

}
