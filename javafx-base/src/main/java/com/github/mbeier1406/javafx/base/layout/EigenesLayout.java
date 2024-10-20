package com.github.mbeier1406.javafx.base.layout;

import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Demonstriert die Nutzung eines eigenen Layouts.
 * @author mbeier
 * @see MeinLayout
 */
public class EigenesLayout extends Application {

	/**
	 * Ordnet die darzustellenden Elemente vertikal an und fügt jeweils dazwischen einen {@linkplain Separator} ein.
	 * @author mbeier
	 */
	public class MeinLayout extends Region {
		@Override
		protected ObservableList<Node> getChildren() {
			return super.getChildren();
		}
		@Override
		protected void layoutChildren() {
			final var children = super
					.getChildren()
					.stream()
					.peek(System.out::println)
					.filter(n -> !(n instanceof Separator))
					.collect(Collectors.toList());
			System.out.println("children.size()="+children.size());
			if ( children.size() > 0 ) {
				ObservableList<Node> observableArrayList = FXCollections.observableArrayList();
				observableArrayList.add(children.get(0));
				for ( int i=1; i < children.size(); i++ ) {
					Node node = children.get(i);
					node.relocate(0, i*50);
					final var separator = new Separator();
					separator.setOrientation(Orientation.HORIZONTAL);
					separator.setMinWidth(100);
					separator.relocate(0, i*50-10);
					observableArrayList.addAll(separator, node);
					System.out.println(String.format("i=%d; l=%d", i, observableArrayList.size()));
				}
				super.getChildren().clear();
				getChildren().addAll(observableArrayList);
			}
			super.layoutChildren();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {

		final var meinLayout = new MeinLayout();
		meinLayout.getChildren().addAll(new Button("Eins"), new Button("Zwei"), new Button("Drei"));
		final var scene = new Scene(meinLayout, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
