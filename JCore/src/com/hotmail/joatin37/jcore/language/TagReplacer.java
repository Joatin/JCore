package com.hotmail.joatin37.jcore.language;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A class used by the Lang class to perform replacements on a message
 * 
 * @author Joatin
 * @since 1.0.0
 */
public abstract class TagReplacer {

	/**
	 * Called by the lang class in order to start replace the tags. First it
	 * performs some default replacements. The it passes the objects over to the
	 * replace() function, which you should override.
	 * 
	 * @param message
	 *            The message to performe replacing on.
	 * @param tag
	 *            The tag used for this message. Just there for replacing the
	 *            tag tag.
	 * @param replacements
	 *            A list of objects used for replacing.
	 * @return The finished message
	 * @throws NullPointerException
	 *             If message or tag is null.
	 * @since 1.0.0
	 */
	public final String doReplace(String message, String tag,
			Object... replacements) {
		if (message == null) {
			throw new NullPointerException();
		}
		if (tag == null) {
			throw new NullPointerException();
		}
		List<Object> list = Arrays.asList(replacements);
		Iterator<Object> it = list.iterator();
		int string = 1;
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof String) {
				message.replaceAll("[text" + string + "]", (String) obj);
				it.remove();
				string++;
				continue;
			}
			if (obj instanceof OfflinePlayer) {
				message.replaceAll("[player]", ((OfflinePlayer) obj).getName());
				it.remove();
				continue;
			}
			if (obj instanceof JavaPlugin) {
				message.replaceAll("[plugin]", ((JavaPlugin) obj).getName());
				message.replaceAll("[version]", ((JavaPlugin) obj)
						.getDescription().getVersion());
				it.remove();
				continue;
			}
			if (obj instanceof Block) {
				this.ParseBlock((Block) obj);
				it.remove();
				continue;
			}
			if (obj instanceof Entity) {
				this.ParseEntity((Entity) obj);
			}
		}
		message.replaceAll("[tag]", tag).replaceAll("[version]", "version")
				.replaceAll("[player]", "player")
				.replaceAll("[block]", "block")
				.replaceAll("[entity]", "entity")
				.replaceAll("[plugin]", "plugin").replaceAll("[text1]", "?")
				.replaceAll("[text2]", "?").replaceAll("[text3]", "?")
				.replaceAll("[text4]", "?").replaceAll("[text5]", "?");

		this.replace(message, list);

		return message;
	}

	/**
	 * You should override this in order to make you own custom replacements.
	 * Note that if there is no object that matches a replacement you should
	 * still do a default replacement.
	 * 
	 * @param message
	 *            The message to perform replacements on
	 * @param replacements
	 *            The object used for replacing.
	 * @since 1.0.0
	 */
	public abstract void replace(String message, List<Object> replacements);

	private String ParseBlock(Block block) {
		return block.getType().toString();

	}

	private String ParseEntity(Entity entity) {
		return entity.getType().toString();

	}
}
