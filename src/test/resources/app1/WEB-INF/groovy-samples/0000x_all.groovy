
class AddColumnUserTaskReminds extends corner.services.migration.BaseMigration {
	def void self_up(){
		//创建表
		create_table("table_a");
		//删除表
		drop_table("table_a");
		//增加列
		add_column("table_a");
		//删除列
		remove_columns("table_a","col1","col2");
		//其他的请见: {@link corner.services.migration.Migration} 里面的方法
	}
}
