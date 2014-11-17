class CreateColors < ActiveRecord::Migration
  def change
    create_table :colors do |t|
      t.string :name
      t.string :favColor
      t.integer :favNum

      t.timestamps
    end
  end
end
