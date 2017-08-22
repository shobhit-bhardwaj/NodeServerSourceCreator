package com.shobhit.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModelCreator {
	public static void createModel(String file, String modelName, String mongoModelName, List<DataBean> dataList, boolean hasSEOData, boolean hasDisplayOrder, boolean hasStatus, String autoIncrementField) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("require(\"../utils/mongo_util\");").append("\n")
			.append("var logger = require(\"../utils/logger\");").append("\n")
			.append("var mongoose = require(\"mongoose\");").append("\n");
		if(autoIncrementField != null && autoIncrementField.length()>0)
			builder.append("var autoIncrement = require(\"mongoose-auto-increment\");").append("\n");

		builder.append("\nvar "+modelName+"MasterSchema = new mongoose.Schema({").append("\n");
		for(DataBean bean : dataList)
			builder.append("\t").append(bean.getAttributeName()).append(": {type: "+bean.getAttributeType()+((bean.isUnique()) ? ", unique: true" : "")+"},").append("\n");

		if(hasSEOData) {
			builder.append("\t").append("seoData: {").append("\n");
			builder.append("\t\t").append("urlName: String,").append("\n");
			builder.append("\t\t").append("metaTitle: String,").append("\n");
			builder.append("\t\t").append("metaKeywords: String,").append("\n");
			builder.append("\t\t").append("metaDescription: String").append("\n");
			builder.append("\t").append("},").append("\n");
		}
		if(hasDisplayOrder)
			builder.append("\t").append("displayOrder: {type: Number},").append("\n");
		if(hasStatus)
			builder.append("\t").append("status: {type: String, enum: [\"ACTIVE\", \"INACTIVE\"]},").append("\n");
		builder.append("\t").append("createBy: Number,").append("\n")
			.append("\t").append("createAt: {type: Date, default: Date.now},").append("\n")
			.append("\t").append("updateAt: {type: Date}").append("\n");

		builder.append("});").append("\n\n")
			.append("module.exports = mongoose.model(\""+modelName+"Master\", "+modelName+"MasterSchema, \""+mongoModelName+"\");");

		if(autoIncrementField != null && autoIncrementField.length()>0) {
			builder.append("\n\n").append(modelName+"MasterSchema.plugin(autoIncrement.plugin, {").append("\n")
				.append("\t").append("model: \""+modelName+"Master\",").append("\n")
				.append("\t").append("field: \""+autoIncrementField+"\",").append("\n")
				.append("\t").append("startAt: 1,").append("\n")
				.append("\t").append("incrementBy: 1").append("\n")
				.append("});");
		}

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}
}