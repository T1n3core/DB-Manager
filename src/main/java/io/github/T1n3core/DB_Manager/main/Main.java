package io.github.T1n3core.DB_Manager.main;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		LafManager.install(new DarculaTheme());

		SwingUtilities.invokeLater(() -> {
			new MainFrame();
		});
	}
}
