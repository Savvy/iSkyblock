package me.savvy.iskyblock.main;

import me.savvy.iskyblock.ISkyblock;
import me.savvy.iskyblock.facades.GridFacade;
import me.savvy.iskyblock.storage.enums.QueryStatements;
import me.savvy.iskyblock.storage.enums.UpdateStatements;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Grid implements GridFacade {

    private Island[][] islandGrid;

    public Grid() {
        islandGrid = new Island[10][10];
      /*  Island island = new Island();
        int x = 0;
        for(int i = 0; i < islandGrid.length; i++){
            for(int j = 0; j < islandGrid[0].length; j++){
                if (x != 5) islandGrid[i][j] = island;
                x++;
            }
        }*/
      load();
    }

    private void load() {
        Bukkit.broadcastMessage("Triggered");
        try {
            PreparedStatement ps = QueryStatements.ISLAND_SELECT.getStatement(ISkyblock.getInstance().getServerSettings().getId());
            ResultSet resultSet = ISkyblock.getInstance().getSqlBuilder().executeQuery(ps);
            while (resultSet.next()) {
                Bukkit.broadcastMessage("Loaded 1");
                Location min = SkyAPI.getInstance().getLocationFromString(resultSet.getString("spawnPadMin"));
                Location max = SkyAPI.getInstance().getLocationFromString(resultSet.getString("spawnPadMax"));
                Island island = new Island
                        (UUID.fromString(resultSet.getString("owner")), resultSet.getInt("coordX"),
                                resultSet.getInt("coordZ"), resultSet.getInt("size"));
                island.setSpawnPadMin(min);
                island.setSpawnPadMax(max);
                islandGrid[island.getIslandX()][island.getIslandZ()] = island;
            }
            ps.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void claimSpace(Island island) {
        if (!isFreeSpace(island.getIslandX(), island.getIslandZ())) return;
        islandGrid[island.getIslandX()][island.getIslandZ()] = island;
            //id (Server), owner uuid, members, coordX, coordZ, size,
            String spawnPadMin = SkyAPI.getInstance().getStringFromLocation(island.getSpawnPadMin());
            String spawnPadMax = SkyAPI.getInstance().getStringFromLocation(island.getSpawnPadMax());
            ISkyblock.getInstance().getSqlBuilder().executeUpdate(
                    UpdateStatements.ISLAND_INSERT.getStatement(ISkyblock.getInstance().getServerSettings().getId(),
                            island.getOwner().toString(),
                            island.getIslandX(),
                            island.getIslandZ(),
                            island.getSize(),
                            spawnPadMin,
                            spawnPadMax));
        // Save Claim
    }

    @Override
    public boolean isFreeSpace(int x, int z) {
        return x < islandGrid.length && z < islandGrid[x].length && islandGrid[x][z] == null;
    }

    @Override
    public boolean isWithinIsland(Location location) {
        boolean b = false;
        Bukkit.broadcastMessage("CHECKING IF BLOCK IS IN ISLAND");
        for (int row = 0; row < islandGrid.length; row++) {
            for (int col = 0; col < islandGrid[row].length; col++) {
                if (islandGrid[row][col] != null) {
                    Bukkit.broadcastMessage("IS IN ISLAND");
                    return islandGrid[row][col].isInIsland(location);
                }
            }
        }
        return b;
    }

    @Override
    public Island getIsland(Location location) {
        for (int row = 0; row < islandGrid.length; row++) {
            for (int col = 0; col < islandGrid[row].length; col++) {
                if (islandGrid[row][col] != null) {
                    if (islandGrid[row][col].isInIsland(location)) {
                        return islandGrid[row][col];
                    }
                }
            }
        }
        return null;
    }


    @Override
    public int[] getFreeSpace() {
        int[] x = new int[2];
        for (int row = 0; row < islandGrid.length; row++) {
            for (int col = 0; col < islandGrid[row].length; col++) {
                if (islandGrid[row][col] == null) {
                    x[0] = (row);
                    x[1] = (col);
                    return x;
                }
            }
        }
        return x;
    }

    @Override
    public Island getIsland(int x, int z) {
        return islandGrid[x][z];
    }

    @Override
    public Island[][] getIslandGrid() {
        return islandGrid;
    }
}
