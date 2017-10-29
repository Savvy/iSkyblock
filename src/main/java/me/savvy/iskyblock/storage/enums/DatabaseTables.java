package me.savvy.iskyblock.storage.enums;

public enum DatabaseTables {

    //SERVER("CREATE TABLE IF NOT EXISTS `servers` (`id` var(255) NOT NULL PRIMARY KEY,`name` varchar(255) NOT NULL);"),
    ISLANDS("CREATE TABLE IF NOT EXISTS `islands` (`id` varchar(255) NOT NULL, `owner` varchar(255) NOT NULL, `members` TEXT NULL, `coordX` int(9) NOT NULL, `coordZ` int(9) NOT NULL, `size` int(9) NOT NULL, `spawnPadMin` varchar(255) NOT NULL, `spawnPadMax` varchar(255) NOT NULL, PRIMARY KEY (id));");
    private String query;

    public String getQuery() {
        return query;
    }
    DatabaseTables(String query) {
        this.query = query;
    }
}
