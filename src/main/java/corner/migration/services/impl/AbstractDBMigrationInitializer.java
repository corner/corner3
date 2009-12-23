/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.migration.services.impl;

import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.services.ApplicationInitializerFilter;
import org.apache.tapestry5.services.Context;

import corner.migration.services.Migration;
import corner.migration.services.MigrationService;
import corner.migration.services.impl.FileUtils.ISortCallback;


/**
 * 抽象的数据库启动管理工具
 * <p>用户启动时候升级数据库结构
 * 

 * @version $Revision$
 * @since 0.0.2
 */
public abstract class AbstractDBMigrationInitializer implements ApplicationInitializerFilter {

	/**
	 * groovy脚本文件名称排序时使用的regular expressions
	 */
	protected static final String GROOVY_SCRIPT_NAME_PATTERN_STR = "([0-9]*)_*.*groovy";
	private static final Pattern GROOVY_SCRIPT_NAME_PATTERN=Pattern.compile(GROOVY_SCRIPT_NAME_PATTERN_STR);
	
	protected static final String GROOVY_SCRIPT_NAME_NO_EXT_PATTERN_STR = "([0-9]*_*.*)\\.groovy";
	private static final Pattern GROOVY_SCRIPT_NAME_NO_EXT_PATTERN=Pattern.compile(GROOVY_SCRIPT_NAME_NO_EXT_PATTERN_STR);
	
	/**
	 * 数据库类型的脚本标识字符串
	 */
	public static final int INDEX_SCRIPT_TYPE_STR = 0; 	/**
	 * 数据库类型的脚本标识字符串
	 */
	public static final int DB_SCRIPT_TYPE_STR = 1;
	private MigrationService migrationService;
	
	public AbstractDBMigrationInitializer(MigrationService migrationService){
		this.migrationService = migrationService;
	}
	
	protected String getPath(Context context,String path) {
		String realPath = context.getRealFile(path).getAbsolutePath();
		if (realPath == null) throw new RuntimeException("Could not find resource " + path);
		// remove any trailing slash
		if (realPath.endsWith("/")) {
			realPath = realPath.substring(0, realPath.length() - 1);
		}
		return realPath;
	}

	/**
	 * 根据DB中SchemaInfo表中的Version字段的内容，判断应该执行的groovy脚本
	 * @param scriptType 执行脚本类型:'INDEX':执行更新index的脚本;'DB':执行更新数据库的脚本
	 * @param dbVersion 数据库的版本
	 * @param dbPath 数据库脚本的路径
	 */
	protected int executeDbScript(int scriptType,int dbVersion,String dbPath) throws Exception{
		
		
		System.out.println("Groovy script type:"+scriptType+" path:["+dbPath+"]");
		
		File[] files = FileUtils.dir(dbPath+File.separator+GROOVY_SCRIPT_NAME_PATTERN_STR).sort(new ISortCallback(){
			public int getSortData(File file) {
				Matcher matcher=GROOVY_SCRIPT_NAME_PATTERN.matcher(file.getName());
				if(matcher.find()){
					return Integer.parseInt(matcher.group(1));
				}
				return -1;
			}
			
		}).getFiles();
		
		//是否存在脚本文件
		if(files==null||files.length==0){
			return 0;
		}
		
		//得到文件中的最大值
		int fileMaxVersion=getFileVersion(files[files.length-1].getName());
		
		//初次建库或者数据库版本和文件版本一致
		if(scriptType==DB_SCRIPT_TYPE_STR&&dbVersion == MigrationServiceImpl.DB_NOTHING||dbVersion>=fileMaxVersion){
			return fileMaxVersion;
		}
		
		GroovyScriptEngine gse = new GroovyScriptEngine(new String[]{dbPath});
		int fileVersion;
		String fileNameWithoutExt;
		for(File file:files){
			fileVersion=getFileVersion(file.getName());
			
			//仅仅对大于dbVersion进行执行
			if(fileVersion>dbVersion){
				fileNameWithoutExt = getFileWithoutExt(file.getName());
				System.out.println("load groovy script file :["+file.getAbsolutePath()+"]");
				Class<?> clazz =  gse.loadScriptByName(fileNameWithoutExt);
		        Migration migration=(Migration) clazz.newInstance();
		        migration.setMigrationService(this.migrationService);
		        migration.self_up();
			}
		}
		return fileMaxVersion;
	}

	/**
	 * 通过给定的文件名来得到文件的前缀名.
	 * @param filename 文件名称
	 * @return 文件的前缀名
	 */
	private String getFileWithoutExt(String filename) {
		Matcher matcher = GROOVY_SCRIPT_NAME_NO_EXT_PATTERN.matcher(filename);
		
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * 通过给定的文件名来得到文件的版本号.
	 * @param filename Groovy文件
	 * @return 文件的版本号
	 */
	private int getFileVersion(String filename){
		Matcher matcher = GROOVY_SCRIPT_NAME_PATTERN.matcher(filename);
		
		if(matcher.find()){
			return Integer.parseInt(matcher.group(1));
		}
		return -1;
	}
	/**
	 * 得到数据库升级的服务类
	 * @return 数据库升级服务类
	 * @since 0.0.2
	 */
	protected MigrationService getMigrationService(){
		return this.migrationService;
	}
}
