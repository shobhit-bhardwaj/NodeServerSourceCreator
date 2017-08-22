package com.shobhit.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RoutesCreator {
	public static void createRoutes(String file, String modelVarName, String modelClassName, boolean hasDisplayOrder) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var "+modelVarName+" = require('./controllers/index');").append("\n\n")
			.append("module.exports = function(app) {").append("\n")
			.append("\t").append("app.post(\"/add"+modelClassName+"\", "+modelVarName+".add"+modelClassName+");").append("\n")
			.append("\t").append("app.post(\"/update"+modelClassName+"\", "+modelVarName+".update"+modelClassName+");").append("\n")
			.append("\t").append("app.post(\"/find"+modelClassName+"ByProperty\", "+modelVarName+".find"+modelClassName+"ByProperty);").append("\n")
			.append("\t").append("app.post(\"/find"+modelClassName+"List\", "+modelVarName+".find"+modelClassName+"List);").append("\n");

		if(hasDisplayOrder)
			builder.append("\t").append("app.post(\"/update"+modelClassName+"DisplayOrder\", "+modelVarName+".update"+modelClassName+"DisplayOrder);").append("\n");

		builder.append("};");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}
}