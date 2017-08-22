import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shobhit.tool.ControllerCreator;
import com.shobhit.tool.DataBean;
import com.shobhit.tool.ModelCreator;
import com.shobhit.tool.RoutesCreator;
import com.shobhit.tool.ServiceCreator;

public class Runner {
	private static final String DIR_PATH = "D:\\Shobhit\\";
	private static final String FOLDER_NAME = "./files/";

	private static class Constants {
		private static final String DIRECTORY_NAME = "directoryName";
		private static final String MODEL_FILE_NAME = "modelFileName";
		private static final String ROUTES_FILE_NAME = "routesFileName";
		private static final String INDEX_FILE_NAME = "indexFileName";
		private static final String ADD_FILE_NAME = "addFileName";
		private static final String FIND_BY_PROPERTY_FILE_NAME = "findByPropertyFileName";
		private static final String FIND_LIST_FILE_NAME = "findListFileName";
		private static final String UPDATE_FILE_NAME = "updateFileName";
		private static final String UPDATE_DISPLAY_ORDER_FILE_NAME = "updateDisplayOrderFileName";

		private static final String MODEL_VAR_NAME = "modelVarName";
		private static final String MODEL_CLASS_NAME = "modelClassName";
		private static final String MONGO_MODEL_NAME = "mongoModelName";
		private static final String MODEL_PARAMETERS = "modelParameters";
		private static final String HAS_SEO_DATA = "hasSEOData";
		private static final String HAS_DISPLAY_ORDER = "hasDisplayOrder";
		private static final String HAS_STATUS = "hasStatus";
		private static final String AUTO_INCREMENT_FIELD = "autoIncrementField";
		private static final String ADD_FIELD_PARAMETERS = "addFieldParameters";
		private static final String CONTROLLER_FIND_FIELD_PARAMETERS = "controllerFindFieldParameters";
		private static final String SERVICE_FIND_FIELD_PARAMETERS = "serviceFindFieldParameters";
		private static final String SERVICE_UPDATE_FIELD_PARAMETERS = "serviceUpdateFieldParameters";

		private static final String MODEL_DIR_NAME = "\\models";
		private static final String CONTROLLER_DIR_NAME = "\\controllers";
		private static final String SERVICE_DIR_NAME = "\\services";

		private static final String JS_EXTENSION = ".js";
	}

	private static void startActivity() throws Exception {
		List<String> fileNames = new ArrayList<>();
		File[] files = new File(FOLDER_NAME).listFiles();
		for (File file : files)
			if (file.isFile() && !file.getName().contains("ignore"))
				fileNames.add(file.getName());

		BufferedReader reader = null;
		Map<String, String> configMap = new HashMap<String, String>();
		String line = null;

		List<DataBean> modelDataList = new ArrayList<DataBean>();
		List<String> addFieldList = new ArrayList<>();
		List<String> controllerFindFieldList = new ArrayList<>();
		List<String> serviceFindFieldList = new ArrayList<>();
		List<String> serviceUpdateFieldList = new ArrayList<>();
		Map<String, String> fileNameMap = new HashMap<String, String>();

		for(String fileName : fileNames) {
			configMap.clear();

			reader = new BufferedReader(new FileReader(new File(FOLDER_NAME+fileName)));
			while ((line = reader.readLine()) != null) {
				if(line.contains("=")) {
					String[] data = line.split("=");
					configMap.put(data[0].trim(), data[1].trim());
				}
			}

			modelDataList.clear();
			addFieldList.clear();
			controllerFindFieldList.clear();
			serviceFindFieldList.clear();
			serviceUpdateFieldList.clear();

			try {
				String directoryName = configMap.get(Constants.DIRECTORY_NAME);
				String modelFileName = configMap.get(Constants.MODEL_FILE_NAME);
				String routesFileName = configMap.get(Constants.ROUTES_FILE_NAME);
				String indexFileName = configMap.get(Constants.INDEX_FILE_NAME);
				String addFileName = configMap.get(Constants.ADD_FILE_NAME);
				String findByPropertyFileName = configMap.get(Constants.FIND_BY_PROPERTY_FILE_NAME);
				String findListFileName = configMap.get(Constants.FIND_LIST_FILE_NAME);
				String updateFileName = configMap.get(Constants.UPDATE_FILE_NAME);
				String updateDisplayOrderFileName = configMap.get(Constants.UPDATE_DISPLAY_ORDER_FILE_NAME);

				String modelVarName = configMap.get(Constants.MODEL_VAR_NAME);
				String modelClassName = configMap.get(Constants.MODEL_CLASS_NAME);
				String mongoModelName = configMap.get(Constants.MONGO_MODEL_NAME);

				String modelParameters = configMap.get(Constants.MODEL_PARAMETERS);
				for(String param : modelParameters.split("_"))
					modelDataList.add(new DataBean(param.split(",")[0], param.split(",")[1], Boolean.parseBoolean(param.split(",")[2])));

				boolean hasSEOData = Boolean.parseBoolean(configMap.get(Constants.HAS_SEO_DATA));
				boolean hasDisplayOrder = Boolean.parseBoolean(configMap.get(Constants.HAS_DISPLAY_ORDER));
				boolean hasStatus = Boolean.parseBoolean(configMap.get(Constants.HAS_STATUS));
				String autoIncrementField = configMap.get(Constants.AUTO_INCREMENT_FIELD);

				for(String addField : configMap.get(Constants.ADD_FIELD_PARAMETERS).split(","))
					addFieldList.add(addField);

				for(String controllerFindField : configMap.get(Constants.CONTROLLER_FIND_FIELD_PARAMETERS).split(","))
					controllerFindFieldList.add(controllerFindField);

				for(String serviceFindField : configMap.get(Constants.SERVICE_FIND_FIELD_PARAMETERS).split(","))
					serviceFindFieldList.add(serviceFindField);

				String updateCriteria = autoIncrementField;
				for(String serviceUpdateField : configMap.get(Constants.SERVICE_UPDATE_FIELD_PARAMETERS).split(","))
					serviceUpdateFieldList.add(serviceUpdateField);

				//	Setting Data Variables Start
				fileNameMap.put(Constants.ADD_FILE_NAME, addFileName);
				fileNameMap.put(Constants.FIND_BY_PROPERTY_FILE_NAME, findByPropertyFileName);
				fileNameMap.put(Constants.FIND_LIST_FILE_NAME, findListFileName);
				fileNameMap.put(Constants.UPDATE_FILE_NAME, updateFileName);
				fileNameMap.put(Constants.UPDATE_DISPLAY_ORDER_FILE_NAME, updateDisplayOrderFileName);
				//	Setting Data Variables End

				//	Creating Directory Structure
				File file = new File(DIR_PATH+directoryName);
				if(!file.exists())
					file.mkdir();

				//	Creating Model
				String path = DIR_PATH+directoryName+Constants.MODEL_DIR_NAME;
				file = new File(path);
				if(!file.exists())
					file.mkdir();
				ModelCreator.createModel(path+"\\"+modelFileName+Constants.JS_EXTENSION, modelClassName, mongoModelName, modelDataList, hasSEOData, hasDisplayOrder, hasStatus, autoIncrementField);

				//	Creating Routes
				path = DIR_PATH+directoryName;
				RoutesCreator.createRoutes(path+"\\"+routesFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, hasDisplayOrder);

				//	Creating Controllers
				path = DIR_PATH+directoryName+Constants.CONTROLLER_DIR_NAME;
				file = new File(path);
				if(!file.exists())
					file.mkdir();
				ControllerCreator.createIndexController(path+"\\"+indexFileName+Constants.JS_EXTENSION, modelClassName, fileNameMap);
				ControllerCreator.addController(path+"\\"+addFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, addFieldList);
				ControllerCreator.findByPropertyController(path+"\\"+findByPropertyFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, controllerFindFieldList);
				ControllerCreator.findListController(path+"\\"+findListFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, controllerFindFieldList);
				ControllerCreator.updateController(path+"\\"+updateFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, addFieldList);
				ControllerCreator.updateDisplayOrderController(path+"\\"+updateDisplayOrderFileName+Constants.JS_EXTENSION, modelVarName, modelClassName);

				//	Creating Service
				path = DIR_PATH+directoryName+Constants.SERVICE_DIR_NAME;
				file = new File(path);
				if(!file.exists())
					file.mkdir();
				ServiceCreator.createIndexService(path+"\\"+indexFileName+Constants.JS_EXTENSION, modelClassName, fileNameMap);
				ServiceCreator.addService(path+"\\"+addFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, modelFileName, addFieldList);
				ServiceCreator.findByPropertyService(path+"\\"+findByPropertyFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, modelFileName, serviceFindFieldList);
				ServiceCreator.findListService(path+"\\"+findListFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, modelFileName, hasStatus, hasDisplayOrder);
				ServiceCreator.updateService(path+"\\"+updateFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, modelFileName, updateCriteria, serviceUpdateFieldList);
				ServiceCreator.updateDisplayOrderService(path+"\\"+updateDisplayOrderFileName+Constants.JS_EXTENSION, modelVarName, modelClassName, modelFileName, updateCriteria);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Runner.startActivity();
	}
}