package com.shobhit.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ServiceCreator {
	public static void createIndexService(String file, String modelName, Map<String, String> fileNameMap) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var add"+modelName+"Service = require(\"./"+fileNameMap.get("addFileName")+"\");").append("\n")
			.append("var find"+modelName+"ByPropertyService = require(\"./"+fileNameMap.get("findByPropertyFileName")+"\");").append("\n")
			.append("var find"+modelName+"ListService = require(\"./"+fileNameMap.get("findListFileName")+"\");").append("\n")
			.append("var update"+modelName+"Service = require(\"./"+fileNameMap.get("updateFileName")+"\");").append("\n")
			.append("var update"+modelName+"DisplayOrderService = require(\"./"+fileNameMap.get("updateDisplayOrderFileName")+"\");").append("\n\n")
			.append("module.exports = {").append("\n")
			.append("\t").append("add"+modelName+" : add"+modelName+"Service,").append("\n")
			.append("\t").append("find"+modelName+"ByProperty : find"+modelName+"ByPropertyService,").append("\n")
			.append("\t").append("find"+modelName+"List : find"+modelName+"ListService,").append("\n")
			.append("\t").append("update"+modelName+" : update"+modelName+"Service,").append("\n")
			.append("\t").append("update"+modelName+"DisplayOrder : update"+modelName+"DisplayOrderService").append("\n")
			.append("};");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void addService(String file, String modelVarName, String modelClassName, String modelFileName, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var "+modelVarName+"Master = require(\"../models/"+modelFileName+"\");").append("\n")
			.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function add"+modelClassName+"(requestObject, callback) {").append("\n")
			.append("\t").append("var new"+modelClassName+" = new "+modelVarName+"Master({").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t\t").append("seoData : {").append("\n");
				builder.append("\t\t\t").append("urlName : requestObject.urlName,").append("\n");
				builder.append("\t\t\t").append("metaTitle : requestObject.metaTitle,").append("\n");
				builder.append("\t\t\t").append("metaKeywords : requestObject.metaKeywords,").append("\n");
				builder.append("\t\t\t").append("metaDescription : requestObject.metaDescription,").append("\n");
				builder.append("\t\t").append("},").append("\n");
			} else
				builder.append("\t\t").append(field+" : requestObject."+field+",").append("\n");
		}
		builder.deleteCharAt(builder.length()-2);

		builder.append("\t").append("});").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append("new"+modelClassName+".save(function(error, data) {").append("\n")
			.append("\t\t").append("if (error) {").append("\n")
			.append("\t\t\t").append("logger.error(error);").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = responseCode.MONGO_ERROR;").append("\n")
			.append("\t\t\t").append("callback(error, responseObject);").append("\n")
			.append("\t\t\t").append("return;").append("\n")
			.append("\t\t").append("}").append("\n")
			.append("\t\t").append("responseObject.responseCode = responseCode.SUCCESS;").append("\n")
			.append("\t\t").append("responseObject.responseData = data;").append("\n")
			.append("\t\t").append("callback(null, responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = add"+modelClassName+";").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("var requestObject = new Object();").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t").append("requestObject.urlName = \"\";").append("\n");
				builder.append("\t").append("requestObject.metaTitle = \"\";").append("\n");
				builder.append("\t").append("requestObject.metaKeywords = \"\";").append("\n");
				builder.append("\t").append("requestObject.metaDescription = \"\";").append("\n");
			} else
				builder.append("\t").append("requestObject."+field+" = \"\";").append("\n");
		}

		builder.append("\t").append("console.log(requestObject);").append("\n").append("\n")
			.append("\t").append("add"+modelClassName+"(requestObject, function(error, responseObject) {").append("\n")
			.append("\t\t").append("console.log(\"Response Code - \" + responseObject.responseCode);").append("\n")
			.append("\t\t").append("if (error)").append("\n")
			.append("\t\t\t").append("console.log(\"Error - \" + error);").append("\n")
			.append("\t\t").append("else").append("\n")
			.append("\t\t\t").append("console.log(\"Response Data - \" + responseObject.responseData);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void findByPropertyService(String file, String modelVarName, String modelClassName, String modelFileName, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var "+modelVarName+"Master = require(\"../models/"+modelFileName+"\");").append("\n")
			.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function find"+modelClassName+"ByProperty(requestObject, callback) {").append("\n")
			.append("\t").append("var query = "+modelVarName+"Master.findOne({});").append("\n");

		for(String field : fieldList) {
			builder.append("\t").append("if(requestObject."+field+")").append("\n");
			builder.append("\t\t").append("query.where(\""+field+"\").equals(requestObject."+field+");").append("\n");
		}

		builder.append("\n\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append("query.exec(function (error, data) {").append("\n")
			.append("\t\t").append("if (error) {").append("\n")
			.append("\t\t\t").append("logger.error(error);").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = responseCode.MONGO_ERROR;").append("\n")
			.append("\t\t\t").append("callback(error, responseObject);").append("\n")
			.append("\t\t\t").append("return;").append("\n")
			.append("\t\t").append("}").append("\n")
			.append("\t\t").append("responseObject.responseCode = responseCode.SUCCESS;").append("\n")
			.append("\t\t").append("responseObject.responseData = data;").append("\n")
			.append("\t\t").append("callback(null, responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = find"+modelClassName+"ByProperty;").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("var requestObject = new Object();").append("\n");

		for(String field : fieldList)
			builder.append("\t").append("//requestObject."+field+" = \""+field+"\";").append("\n");

		builder.append("\n\t").append("find"+modelClassName+"ByProperty(requestObject, function(error, responseObject) {").append("\n")
			.append("\t\t").append("console.log(\"Response Code - \" + responseObject.responseCode);").append("\n")
			.append("\t\t").append("if (error)").append("\n")
			.append("\t\t\t").append("console.log(\"Error - \" + error);").append("\n")
			.append("\t\t").append("else").append("\n")
			.append("\t\t\t").append("console.log(\"Response Data - \" + responseObject.responseData);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void findListService(String file, String modelVarName, String modelClassName, String modelFileName, boolean hasStatus, boolean hasDisplayOrder) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var "+modelVarName+"Master = require(\"../models/"+modelFileName+"\");").append("\n")
			.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function find"+modelClassName+"List(requestObject, callback) {").append("\n")
			.append("\t").append("var query = "+modelVarName+"Master.find({});").append("\n");

		if(hasStatus) {
			builder.append("\t").append("if(requestObject.status)").append("\n")
				.append("\t\t").append("query.where(\"status\").equals(requestObject.status);").append("\n");
		}
		if(hasDisplayOrder)
			builder.append("\t").append("query.sort(\"displayOrder\");").append("\n\n");

		builder.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append("query.exec(function (error, data) {").append("\n")
			.append("\t\t").append("if (error) {").append("\n")
			.append("\t\t\t").append("logger.error(error);").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = responseCode.MONGO_ERROR;").append("\n")
			.append("\t\t\t").append("callback(error, responseObject);").append("\n")
			.append("\t\t\t").append("return;").append("\n")
			.append("\t\t").append("}").append("\n")
			.append("\t\t").append("responseObject.responseCode = responseCode.SUCCESS;").append("\n")
			.append("\t\t").append("responseObject.responseData = data;").append("\n")
			.append("\t\t").append("callback(null, responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = find"+modelClassName+"List;").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("var requestObject = new Object();").append("\n")
			.append("\t").append("//requestObject.status = \"ACTIVE\";").append("\n\n")
			.append("\t").append("find"+modelClassName+"List(requestObject, function(error, responseObject) {").append("\n")
			.append("\t\t").append("console.log(\"Response Code - \" + responseObject.responseCode);").append("\n")
			.append("\t\t").append("if (error)").append("\n")
			.append("\t\t\t").append("console.log(\"Error - \" + error);").append("\n")
			.append("\t\t").append("else").append("\n")
			.append("\t\t\t").append("console.log(\"Response Data - \" + responseObject.responseData);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void updateService(String file, String modelVarName, String modelClassName, String modelFileName, String updateCriteria, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var "+modelVarName+"Master = require(\"../models/"+modelFileName+"\");").append("\n")
			.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function update"+modelClassName+"(requestObject, callback) {").append("\n")
			.append("\t").append("var updateObject = new Object();").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t").append("if(requestObject.urlName)").append("\n")
					.append("\t\t").append("updateObject[\"seoData.urlName\"] = requestObject.urlName;").append("\n");
				builder.append("\t").append("if(requestObject.metaTitle)").append("\n")
					.append("\t\t").append("updateObject[\"seoData.metaTitle\"] = requestObject.metaTitle;").append("\n");
				builder.append("\t").append("if(requestObject.metaKeywords)").append("\n")
					.append("\t\t").append("updateObject[\"seoData.metaKeywords\"] = requestObject.metaKeywords;").append("\n");
				builder.append("\t").append("if(requestObject.metaDescription)").append("\n")
					.append("\t\t").append("updateObject[\"seoData.metaDescription\"] = requestObject.metaDescription;").append("\n");
			} else {
				builder.append("\t").append("if(requestObject."+field+")").append("\n")
					.append("\t\t").append("updateObject."+field+" = requestObject."+field+";").append("\n");
			}
		}

		builder.append("\t").append("updateObject.updateAt = new Date();").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append("var query = {"+updateCriteria+": requestObject."+updateCriteria+"};").append("\n")
			.append("\t").append(modelVarName+"Master.findOneAndUpdate(query, updateObject, function (error, data) {").append("\n")
			.append("\t\t").append("if (error) {").append("\n")
			.append("\t\t\t").append("logger.error(error);").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = responseCode.MONGO_ERROR;").append("\n")
			.append("\t\t\t").append("callback(error, responseObject);").append("\n")
			.append("\t\t\t").append("return;").append("\n")
			.append("\t\t").append("}").append("\n")
			.append("\t\t").append("responseObject.responseCode = responseCode.SUCCESS;").append("\n")
			.append("\t\t").append("responseObject.responseData = data;").append("\n")
			.append("\t\t").append("callback(null, responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = update"+modelClassName+";").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("var requestObject = new Object();").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t").append("requestObject.urlName = \"\";").append("\n");
				builder.append("\t").append("requestObject.metaTitle = \"\";").append("\n");
				builder.append("\t").append("requestObject.metaKeywords = \"\";").append("\n");
				builder.append("\t").append("requestObject.metaDescription = \"\";").append("\n");
			} else
				builder.append("\t").append("requestObject."+field+" = \"\";").append("\n");
		}

		builder.append("\t").append("console.log(requestObject);").append("\n").append("\n")
			.append("\t").append("update"+modelClassName+"(requestObject, function(error, responseObject) {").append("\n")
			.append("\t\t").append("console.log(\"Response Code - \" + responseObject.responseCode);").append("\n")
			.append("\t\t").append("if (error)").append("\n")
			.append("\t\t\t").append("console.log(\"Error - \" + error);").append("\n")
			.append("\t\t").append("else").append("\n")
			.append("\t\t\t").append("console.log(\"Response Data - \" + responseObject.responseData);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void updateDisplayOrderService(String file, String modelVarName, String modelClassName, String modelFileName, String updateCriteria) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var "+modelVarName+"Master = require(\"../models/"+modelFileName+"\");").append("\n")
			.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n")
			.append("var async = require(NODE_PATH + \"async\");").append("\n\n")
			.append("function update"+modelClassName+"DisplayOrder(requestObject, callback) {").append("\n")
			.append("\t").append("var currentRowNumber = 0;").append("\n")
			.append("\t").append("async.each(requestObject."+modelVarName+"List, function(currentRow, cb) {").append("\n")
			.append("\t\t").append("currentRowNumber++;").append("\n")
			.append("\t\t").append("console.log('currentRow - ', currentRow);").append("\n\n")
			.append("\t\t").append("var updateObject = new Object();").append("\n")
			.append("\t\t").append("updateObject.displayOrder = currentRowNumber;").append("\n")
			.append("\t\t").append("var responseObject = new Object();").append("\n")
			.append("\t\t").append("var query = {"+updateCriteria+" : currentRow."+updateCriteria+"};").append("\n")
			.append("\t\t").append(modelVarName+"Master.findOneAndUpdate(query, updateObject, function(error, data) {").append("\n")
			.append("\t\t\t").append("if (error) {").append("\n")
			.append("\t\t\t\t").append("logger.error(error);").append("\n")
			.append("\t\t\t\t").append("cb(error);").append("\n")
			.append("\t\t\t\t").append("return;").append("\n")
			.append("\t\t\t").append("}").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = responseCode.SUCCESS;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data;").append("\n")
			.append("\t\t\t").append("cb();").append("\n")
			.append("\t\t").append("});").append("\n")
			.append("\t").append("}, function(error) {").append("\n")
			.append("\t\t").append("if (error) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = responseCode.MONGO_ERROR;").append("\n")
			.append("\t\t\t").append("callback(error, responseObject);").append("\n")
			.append("\t\t\t").append("return;").append("\n")
			.append("\t\t").append("}").append("\n")
			.append("\t\t").append("responseObject.responseCode = responseCode.SUCCESS;").append("\n")
			.append("\t\t").append("callback(null, responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = update"+modelClassName+"DisplayOrder;").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("var "+modelVarName+"List = [ {").append("\n")
			.append("\t\t").append(updateCriteria+" : 1,").append("\n")
			.append("\t\t").append("displayOrder : 1").append("\n")
			.append("\t").append("}, {").append("\n")
			.append("\t\t").append(updateCriteria+" : 2,").append("\n")
			.append("\t\t").append("displayOrder : 1").append("\n")
			.append("\t").append("} ];").append("\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append("requestObject."+modelVarName+"List = "+modelVarName+"List;").append("\n")
			.append("\t").append("console.log(requestObject);").append("\n\n")
			.append("\t").append("update"+modelClassName+"(requestObject, function(error, responseObject) {").append("\n")
			.append("\t\t").append("console.log(\"Response Code - \" + responseObject.responseCode);").append("\n")
			.append("\t\t").append("if (error)").append("\n")
			.append("\t\t\t").append("console.log(\"Error - \" + error);").append("\n")
			.append("\t\t").append("else").append("\n")
			.append("\t\t\t").append("console.log(\"Response Data - \" + responseObject.responseData);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}
}