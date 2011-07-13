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
package net.sourceforge.shelled.ui.tests;

import net.sourceforge.shelled.ui.text.tests.TextSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * All tests wrapper.
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TextSuite.class })
public class AllTests {
}
