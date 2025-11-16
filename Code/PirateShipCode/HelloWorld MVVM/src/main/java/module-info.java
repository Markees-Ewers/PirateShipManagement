module edu.westga.cs3211.helloworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires javafx.base;

    opens edu.westga.cs3211.pirateship.view to javafx.fxml;
    exports edu.westga.cs3211.pirateship;
}
