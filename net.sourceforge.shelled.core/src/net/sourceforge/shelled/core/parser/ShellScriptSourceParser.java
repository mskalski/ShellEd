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
package net.sourceforge.shelled.core.parser;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.MethodCallExpression;
import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.compiler.problem.IProblemReporter;

public class ShellScriptSourceParser extends AbstractSourceParser {

	// @Override
	public ModuleDeclaration parse(char[] fileName, char[] source,
			IProblemReporter reporter) {
		ShellModuleDeclaration moduleDeclaration = new ShellModuleDeclaration(
				source.length);

		ShellModel shellModel = parse(new CharArrayReader(source),
				moduleDeclaration);
		moduleDeclaration.setFunctions(shellModel.getFunctions());
		processNode(shellModel, moduleDeclaration);
		return moduleDeclaration;
	}

	private ShellModel parse(CharArrayReader reader,
			ShellModuleDeclaration moduleDeclaration) {
		ShellModel model = new ShellModel();

		BufferedReader bReader = new BufferedReader(reader);
		String line;
		int lineStart = 0;
		Set<String> functionNames = new HashSet<String>();
		MethodDeclaration mDeclaration = null;
		try {
			while ((line = bReader.readLine()) != null) {
				if (line.contains("() {")) {

					mDeclaration = new MethodDeclaration(line.substring(0, line
							.indexOf('(')), lineStart, line.indexOf(')')
							+ lineStart, line.indexOf('{') + lineStart, line
							.indexOf('{')
							+ lineStart);
					functionNames.add(line.substring(0, line.indexOf('('))
							.trim());
					model.addFunction(mDeclaration);
				} else if (line.contains("}")) {
					if (mDeclaration != null) {
						mDeclaration.setEnd(lineStart + line.length());
					}
				}
				for (String funcName : functionNames) {
					if (line.contains(funcName)) {// TODO find real occurances
						moduleDeclaration
								.addStatement(new MethodCallExpression(
										lineStart, lineStart + line.length(),
										null, funcName, null));
					}
				}

				lineStart += line.length() + 1;
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}

	private void processNode(ShellModel parse,
			ModuleDeclaration moduleDeclaration) {
		for (MethodDeclaration functionNode : parse.getFunctions()) {
			moduleDeclaration.addStatement(functionNode);
		}
	}

}
