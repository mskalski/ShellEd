/*******************************************************************************
 * Copyright (c) 2014 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Peter Palaga - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.preferences;

import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.ui.text.folding.DefaultFoldingPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.text.folding.IFoldingPreferenceBlock;
import org.eclipse.jface.preference.PreferencePage;

import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.text.folding.ShellCodeFoldingPreferenceBlock;
import net.sourceforge.shelled.ui.text.folding.ShellCommentFoldingPreferenceBlock;

/**
 * A couple of usual folding options.
 */
public class ShellFoldingPreferencePage extends AbstractConfigurationBlockPreferencePage {
	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		return new DefaultFoldingPreferenceConfigurationBlock(overlayPreferenceStore, this) {
			@Override
			protected IFoldingPreferenceBlock createDocumentationBlock(OverlayPreferenceStore store,
					PreferencePage page) {
				return new ShellCommentFoldingPreferenceBlock(store, page);
			}

			@Override
			protected IFoldingPreferenceBlock createSourceCodeBlock(OverlayPreferenceStore store, PreferencePage page) {
				return new ShellCodeFoldingPreferenceBlock(store, page);
			}
		};
	}
}
