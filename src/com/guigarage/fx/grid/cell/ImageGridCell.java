package com.guigarage.fx.grid.cell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;

import com.guigarage.fx.grid.GridCell;

public class ImageGridCell extends GridCell<Image> {
	
	public ImageGridCell() {
		getStyleClass().add("image-grid-cell");
		itemProperty().addListener(new ChangeListener<Image>() {

			@Override
			public void changed(ObservableValue<? extends Image> arg0,
					Image arg1, Image arg2) {
				getChildren().clear();
				//TODO: Die Properties des ImageView sollten ins CSS wandern
				ImageView imageView = ImageViewBuilder.create().image(arg2).build();
				imageView.fitHeightProperty().bind(heightProperty());
				imageView.fitWidthProperty().bind(widthProperty());
				
				setGraphic(imageView);
			}
		});
	}
}