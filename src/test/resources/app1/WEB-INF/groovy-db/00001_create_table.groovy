
class SampleCreateTable extends corner.services.migration.BaseMigration {
	def void self_up(){
		create_table("table_a");
	}
}
