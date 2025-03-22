package com.johan.create.foundation.config.ui;

import net.minecraft.client.gui.Font;

public class ConfigTextField extends HintableTextFieldWidget {

	public ConfigTextField(Font font, int x, int y, int width, int height) {
		super(font, x, y, width, height);
	}

	@Override
	public void setFocus(boolean focus) {
		super.setFocus(focus);

		if (!focus) {
			if (ConfigScreenList.currentText == this)
				ConfigScreenList.currentText = null;

			return;
		}

		if (ConfigScreenList.currentText != null && ConfigScreenList.currentText != this)
			ConfigScreenList.currentText.setFocus(false);

		ConfigScreenList.currentText = this;
	}
}
