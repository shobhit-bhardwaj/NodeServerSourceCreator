package com.shobhit.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControllerCreator {
	public static void createIndexController(String file, String modelName, Map<String, String> fileNameMap) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var add"+modelName+"Api = require(\"./"+fileNameMap.get("addFileName")+"\");").append("\n")
			.append("var find"+modelName+"ByPropertyApi = require(\"./"+fileNameMap.get("findByPropertyFileName")+"\");").append("\n")
			.append("var find"+modelName+"ListApi = require(\"./"+fileNameMap.get("findListFileName")+"\");").append("\n")
			.append("var update"+modelName+"Api = require(\"./"+fileNameMap.get("updateFileName")+"\");").append("\n")
			.append("var update"+modelName+"DisplayOrderApi = require(\"./"+fileNameMap.get("updateDisplayOrderFileName")+"\");").append("\n\n")
			.append("module.exports = {").append("\n")
			.append("\t").append("add"+modelName+" : add"+modelName+"Api,").append("\n")
			.append("\t").append("find"+modelName+"ByProperty : find"+modelName+"ByPropertyApi,").append("\n")
			.append("\t").append("find"+modelName+"List : find"+modelName+"ListApi,").append("\n")
			.append("\t").append("update"+modelName+" : update"+modelName+"Api,").append("\n")
			.append("\t").append("update"+modelName+"DisplayOrder : update"+modelName+"DisplayOrderApi").append("\n")
			.append("};");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void addController(String file, String modelVarName, String modelClassName, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var "+modelVarName+"Service = require(\"../"+modelVarName+"/services/index\");").append("\n")
			.append("var responseMessage = require(\"../../utils/response_message\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function add"+modelClassName+"(request, response, next) {").append("\n")
			.append("\t").append("var requestObject = request.body;").append("\n")
			.append("\t").append("//console.log(\"add"+modelClassName+" API :- Request - %j\", requestObject);").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append(modelVarName+"Service.add"+modelClassName+"(requestObject, function(error, data) {").append("\n")
			.append("\t\t").append("if(error === null) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("} else if(data.responseCode !== responseCode.SUCCESS) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = {};").append("\n")
			.append("\t\t\t").append("responseObject.responseData.message = responseMessage[data.responseCode];").append("\n")
			.append("\t\t").append("} else {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("}").append("\n\n")
			.append("\t\t").append("logger.info(\"add"+modelClassName+" API :- Response - %j\", responseObject);").append("\n")
			.append("\t\t").append("response.json(responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = add"+modelClassName+";").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("(function() {").append("\n")
			.append("\t\t").append("var request = {};").append("\n")
			.append("\t\t").append("var response = {").append("\n")
			.append("\t\t\t").append("json : function(result) {").append("\n")
			.append("\t\t\t\t").append("console.log(JSON.stringify(result, null, 2));").append("\n")
			.append("\t\t\t").append("}").append("\n")
			.append("\t\t").append("};").append("\n\n")
			.append("\t\t").append("var requestObject = new Object();").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t\t").append("requestObject.urlName = \"\";").append("\n");
				builder.append("\t\t").append("requestObject.metaTitle = \"\";").append("\n");
				builder.append("\t\t").append("requestObject.metaKeywords = \"\";").append("\n");
				builder.append("\t\t").append("requestObject.metaDescription = \"\";").append("\n");
			} else
				builder.append("\t\t").append("requestObject."+field+" = \"\";").append("\n");
		}

		builder.append("\n\t\t").append("console.log(\"Request Data - \" + requestObject);").append("\n")
			.append("\t\t").append("request.body = requestObject;").append("\n")
			.append("\t\t").append("add"+modelClassName+"(request, response);").append("\n")
			.append("\t").append("})();").append("\n")
			.append("}");

	writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void findByPropertyController(String file, String modelVarName, String modelClassName, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var "+modelVarName+"Service = require(\"../"+modelVarName+"/services/index\");").append("\n")
			.append("var responseMessage = require(\"../../utils/response_message\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function find"+modelClassName+"ByProperty(request, response, next) {").append("\n")
			.append("\t").append("var requestObject = request.body;").append("\n")
			.append("\t").append("//console.log(\"find"+modelClassName+"ByProperty API :- Request - %j\", requestObject);").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append(modelVarName+"Service.find"+modelClassName+"ByProperty(requestObject, function(error, data) {").append("\n")
			.append("\t\t").append("if(error === null) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("} else if(data.responseCode !== responseCode.SUCCESS) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = {};").append("\n")
			.append("\t\t\t").append("responseObject.responseData.message = responseMessage[data.responseCode];").append("\n")
			.append("\t\t").append("} else {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n\n")
			.append("\t\t\t").append("var currentRow = data.responseData;").append("\n")
			.append("\t\t\t").append("var currentObj = {};").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t\t\t").append("currentObj.urlName = currentRow.seoData.urlName;").append("\n");
				builder.append("\t\t\t").append("currentObj.metaTitle = currentRow.seoData.metaTitle;").append("\n");
				builder.append("\t\t\t").append("currentObj.metaKeywords = currentRow.seoData.metaKeywords;").append("\n");
				builder.append("\t\t\t").append("currentObj.metaDescription = currentRow.seoData.metaDescription;").append("\n");
			} else
				builder.append("\t\t\t").append("currentObj."+field+" = currentRow."+field+";").append("\n");
		}

		builder.append("\n\t\t\t").append("responseObject.responseData = currentObj;").append("\n")
			.append("\t\t").append("}").append("\n\n")
			.append("\t\t").append("logger.info(\"find"+modelClassName+"ByProperty API :- Response - %j\", responseObject);").append("\n")
			.append("\t\t").append("response.json(responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = find"+modelClassName+"ByProperty;").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("(function() {").append("\n")
			.append("\t\t").append("var request = {};").append("\n")
			.append("\t\t").append("var response = {").append("\n")
			.append("\t\t\t").append("json : function(result) {").append("\n")
			.append("\t\t\t\t").append("console.log(JSON.stringify(result, null, 2));").append("\n")
			.append("\t\t\t").append("}").append("\n")
			.append("\t\t").append("};").append("\n\n")
			.append("\t\t").append("var requestObject = new Object();").append("\n");

		for(String field : fieldList)
			builder.append("\t\t").append("//requestObject."+field+" = \""+field+"\";").append("\n");

		builder.append("\n\t\t").append("console.log(\"Request Data - \" + requestObject);").append("\n")
			.append("\t\t").append("request.body = requestObject;").append("\n")
			.append("\t\t").append("find"+modelClassName+"ByProperty(request, response);").append("\n")
			.append("\t").append("})();").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void findListController(String file, String modelVarName, String modelClassName, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var "+modelVarName+"Service = require(\"../"+modelVarName+"/services/index\");").append("\n")
			.append("var responseMessage = require(\"../../utils/response_message\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function find"+modelClassName+"List(request, response, next) {").append("\n")
			.append("\t").append("var requestObject = request.body;").append("\n")
			.append("\t").append("//console.log(\"find"+modelClassName+"List API :- Request - %j\", requestObject);").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append(modelVarName+"Service.find"+modelClassName+"List(requestObject, function(error, data) {").append("\n")
			.append("\t\t").append("if(error === null) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("} else if(data.responseCode !== responseCode.SUCCESS) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = {};").append("\n")
			.append("\t\t\t").append("responseObject.responseData.message = responseMessage[data.responseCode];").append("\n")
			.append("\t\t").append("} else {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n\n")
			.append("\t\t\t").append("var "+modelVarName+"Arr = data.responseData;").append("\n")
			.append("\t\t\t").append("var "+modelVarName+"List = [];").append("\n")
			.append("\t\t\t").append("for (var i=0, length="+modelVarName+"Arr.length; i<length; i++) {").append("\n")
			.append("\t\t\t\t").append("var currentRow = "+modelVarName+"Arr[i];").append("\n")
			.append("\t\t\t\t").append("var currentObj = {};").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t\t\t\t").append("currentObj.urlName = currentRow.seoData.urlName;").append("\n");
				builder.append("\t\t\t\t").append("currentObj.metaTitle = currentRow.seoData.metaTitle;").append("\n");
				builder.append("\t\t\t\t").append("currentObj.metaKeywords = currentRow.seoData.metaKeywords;").append("\n");
				builder.append("\t\t\t\t").append("currentObj.metaDescription = currentRow.seoData.metaDescription;").append("\n");
			} else
				builder.append("\t\t\t\t").append("currentObj."+field+" = currentRow."+field+";").append("\n");
		}

		builder.append("\t\t\t\t").append(""+modelVarName+"List.push(currentObj);").append("\n")
			.append("\t\t\t").append("}").append("\n")
			.append("\t\t\t").append("responseObject.responseData = "+modelVarName+"List;").append("\n")
			.append("\t\t").append("}").append("\n\n")
			.append("\t\t").append("logger.info(\"find"+modelClassName+"List API :- Response - %j\", responseObject);").append("\n")
			.append("\t\t").append("response.json(responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = find"+modelClassName+"List;").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("(function() {").append("\n")
			.append("\t\t").append("var request = {};").append("\n")
			.append("\t\t").append("var response = {").append("\n")
			.append("\t\t\t").append("json : function(result) {").append("\n")
			.append("\t\t\t\t").append("console.log(JSON.stringify(result, null, 2));").append("\n")
			.append("\t\t\t").append("}").append("\n")
			.append("\t\t").append("};").append("\n\n")
			.append("\t\t").append("var requestObject = new Object();").append("\n")
			.append("\t\t").append("//requestObject.status = \"ACTIVE\";").append("\n")
			.append("\n\t\t").append("console.log(\"Request Data - \" + requestObject);").append("\n")
			.append("\t\t").append("request.body = requestObject;").append("\n")
			.append("\t\t").append("find"+modelClassName+"List(request, response);").append("\n")
			.append("\t").append("})();").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void updateController(String file, String modelVarName, String modelClassName, List<String> fieldList) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var "+modelVarName+"Service = require(\"../"+modelVarName+"/services/index\");").append("\n")
			.append("var responseMessage = require(\"../../utils/response_message\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n")
			.append("function update"+modelClassName+"(request, response, next) {").append("\n")
			.append("\t").append("var requestObject = request.body;").append("\n")
			.append("\t").append("//console.log(\"update"+modelClassName+" API :- Request - %j\", requestObject);").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append(modelVarName+"Service.update"+modelClassName+"(requestObject, function(error, data) {").append("\n")
			.append("\t\t").append("if(error === null) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("} else if(data.responseCode !== responseCode.SUCCESS) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = {};").append("\n")
			.append("\t\t\t").append("responseObject.responseData.message = responseMessage[data.responseCode];").append("\n")
			.append("\t\t").append("} else {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("}").append("\n\n")
			.append("\t\t").append("logger.info(\"update"+modelClassName+" API :- Response - %j\", responseObject);").append("\n")
			.append("\t\t").append("response.json(responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = update"+modelClassName+";").append("\n\n");

		builder.append("// Unit Test Case").append("\n")
			.append("if (require.main === module) {").append("\n")
			.append("\t").append("(function() {").append("\n")
			.append("\t\t").append("var request = {};").append("\n")
			.append("\t\t").append("var response = {").append("\n")
			.append("\t\t\t").append("json : function(result) {").append("\n")
			.append("\t\t\t\t").append("console.log(JSON.stringify(result, null, 2));").append("\n")
			.append("\t\t\t").append("}").append("\n")
			.append("\t\t").append("};").append("\n\n")
			.append("\t\t").append("var requestObject = new Object();").append("\n");

		for(String field : fieldList) {
			if("seoData".equals(field)) {
				builder.append("\t\t").append("requestObject.urlName = \"\";").append("\n");
				builder.append("\t\t").append("requestObject.metaTitle = \"\";").append("\n");
				builder.append("\t\t").append("requestObject.metaKeywords = \"\";").append("\n");
				builder.append("\t\t").append("requestObject.metaDescription = \"\";").append("\n");
			} else
				builder.append("\t\t").append("requestObject."+field+" = \"\";").append("\n");
		}

		builder.append("\n\t\t").append("console.log(\"Request Data - \" + requestObject);").append("\n")
			.append("\t\t").append("request.body = requestObject;").append("\n")
			.append("\t\t").append("update"+modelClassName+"(request, response);").append("\n")
			.append("\t").append("})();").append("\n")
			.append("}");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}

	public static void updateDisplayOrderController(String file, String modelVarName, String modelClassName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

		StringBuilder builder = new StringBuilder();
		builder.append("var logger = require(\"../../utils/logger\");").append("\n")
			.append("var "+modelVarName+"Service = require(\"../"+modelVarName+"/services/index\");").append("\n")
			.append("var responseMessage = require(\"../../utils/response_message\");").append("\n")
			.append("var responseCode = require(\"../../utils/response_code\");").append("\n\n");

		builder.append("function update"+modelClassName+"DisplayOrder(request, response, next) {").append("\n")
			.append("\t").append("var requestObject = request.body;").append("\n")
			.append("\t").append("//console.log(\"update"+modelClassName+"DisplayOrder API :- Request - %j\", requestObject);").append("\n\n")
			.append("\t").append("var responseObject = new Object();").append("\n")
			.append("\t").append(modelVarName+"Service.update"+modelClassName+"DisplayOrder(requestObject, function(error, data) {").append("\n")
			.append("\t\t").append("if(error === null) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("} else if(data.responseCode !== responseCode.SUCCESS) {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = {};").append("\n")
			.append("\t\t\t").append("responseObject.responseData.message = responseMessage[data.responseCode];").append("\n")
			.append("\t\t").append("} else {").append("\n")
			.append("\t\t\t").append("responseObject.responseCode = data.responseCode;").append("\n")
			.append("\t\t\t").append("responseObject.responseData = data.responseData;").append("\n")
			.append("\t\t").append("}").append("\n\n")
			.append("\t\t").append("logger.info(\"update"+modelClassName+"DisplayOrder API :- Response - %j\", responseObject);").append("\n")
			.append("\t\t").append("response.json(responseObject);").append("\n")
			.append("\t").append("});").append("\n")
			.append("}").append("\n\n")
			.append("module.exports = update"+modelClassName+"DisplayOrder;");

		writer.write(builder.toString());
		writer.flush();
		writer.close();
	}
}