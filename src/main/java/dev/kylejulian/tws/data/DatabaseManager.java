package dev.kylejulian.tws.data;

import java.sql.Connection;
import java.sql.SQLException;

import dev.kylejulian.tws.data.interfaces.IDatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import dev.kylejulian.tws.data.callbacks.BooleanQueryCallback;

public abstract class DatabaseManager implements IDatabaseManager {

	private final DatabaseConnectionManager databaseConnectionManager;
	private final JavaPlugin plugin;

	public DatabaseManager(JavaPlugin plugin, DatabaseConnectionManager databaseConnectionManager) {
		this.plugin = plugin;
		this.databaseConnectionManager = databaseConnectionManager;
	}

	/**
	 * Setups the Default Database schema for the Database Manager
	 * 
	 * @param callback The result of the Query as a {@link BooleanQueryCallback}
	 */
	public abstract void setupDefaultSchema(final BooleanQueryCallback callback);

	protected JavaPlugin getPlugin() {
		return this.plugin;
	}

	/**
	 * Queues up a Runnable task on the Server Scheduler asynchronously
	 * 
	 * @param runnable
	 * @return The created BukkitTask
	 */
	protected BukkitTask executeQueryAsync(Runnable runnable) {
		return this.getPlugin().getServer().getScheduler().runTaskAsynchronously(this.plugin, runnable);
	}

	/**
	 * Queues up a Runnable task on the Server scheduler synchronously
	 * 
	 * @param runnable
	 * @return
	 */
	protected BukkitTask executeQuerySync(Runnable runnable) {
		return this.getPlugin().getServer().getScheduler().runTask(this.plugin, runnable);
	}

	/**
	 * Gets a Connection from the Connection pool
	 * 
	 * @return Returns the created or existing Connection
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return this.databaseConnectionManager.getConnectionPool().getConnection();
	}
}