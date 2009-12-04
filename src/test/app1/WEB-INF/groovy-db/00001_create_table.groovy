
class SampleCreateTable extends corner.migration.BaseMigration {
	def void self_up(){
		create_table("table_a");
	}
}
