# Natural Disasters Monitoring and Reporting System

A comprehensive Java desktop application designed for managing, analyzing, and reporting natural disaster measurement data. Built using Layered and MVC Architecture, interactive visualization tools including Gantt and Line charts, and a dynamic multi-format reporting engine.

## Features

- Advanced Visualizations and Analytics:
  - Gantt Charts: Visual timeline tracking for disaster occurrences, durations, and monitoring periods.
  - Line Charts: Single-series and multi-series trend analysis of measurement metrics.
  - Interactive Data Grid: Structured data viewing, sorting, and filtering via JTable.
- Multi-Format Reporting Subsystem:
  - Export dynamic analytical reports in HTML, Markdown, and Plain Text.
  - Modular design allows seamless integration of new report output formats.
- Statistical Processing:
  - Integrated with Apache Commons Math for accurate data metrics and calculations.
- Extensible Architecture:
  - Strongly typed abstractions using Java Interfaces (IMainController, IMeasurementDatabase).
  - Adherence to SOLID design principles such as Dependency Inversion and Interface Segregation.

## Architecture and Design Patterns

The project is structured according to Layered Architecture and the Model-View-Controller (MVC) pattern:

- app / app.gui (View): Swing and AWT presentation layer containing UI frames, dialogs, and chart viewers.
- engine (Controller): Application control logic executing actions and connecting UI requests with domain processing.
- dom2app (Model): Core domain models, data vectors, and database abstractions.
- dom2app.reporting (Subsystem): Strategy-based generation engine for HTML, Markdown, and Text reports.

## Tech Stack and Dependencies

- Language: Java (JDK 8+)
- GUI Framework: Java Swing / AWT
- Charting and Libraries: JFreeChart, Apache Commons Math 3.6.1
- IDE: Eclipse
- Version Control: Git / GitHub

## Project Structure

```text
src/
  app/
    gui/              # GUI components and Chart Viewers (Gantt, Line, Table)
  engine/             # Controller logic (IMainController implementation)
  dom2app/            # Domain logic and Database interfaces
    reporting/        # Report exporters (HTML, Markdown, Text)
lib/                  # External JAR libraries (Apache Commons, JFreeChart)
README.md ```

## How to Run

    Clone the Repository:
    git clone https://github.com/your-username/NaturalDisasters-Analysis.git

    Import into Eclipse or your preferred IDE:
    Go to File -> Import -> Existing Projects into Workspace.

    Configure Build Path:
    Ensure all JAR files in the lib directory are added to the project Java Build Path.

    Launch Application:
    Run app.gui.AppStarter.java as a Java Application.
