# Tyrell Manager

![Java](https://img.shields.io/badge/Java-23-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-23-4285F4?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

**Tyrell Manager** est une application de bureau moderne et intuitive développée en JavaFX pour la gestion efficace des offres d'emploi et des candidatures. Conçue pour offrir une expérience utilisateur fluide, elle intègre des fonctionnalités avancées de visualisation de données et de gestion de base de données.

## Fonctionnalités Clés

*   **Tableau de Bord Interactif** : Vue d'overview des statistiques clés avec des graphiques dynamiques.
*   **Gestion des Offres (CRUD)** : Création, lecture, mise à jour et suppression facile des descriptions de poste.
*   **Recherche et Filtrage** : Outils puissants pour trouver rapidement des informations spécifiques.
*   **Interface Moderne** : Utilisation de bibliothèques UI avancées comme *BootstrapFX* et *TilesFX* pour un design épuré.
*   **Exportation de Données** : Fonctionnalités d'export pour l'analyse externe.

## Stack Technique

*   **Langage** : Java 23
*   **Framework UI** : JavaFX 23
*   **Base de Données** : MySQL 8.0
*   **Gestion de Projet** : Maven
*   **Bibliothèques Clés** :
    *   `FormsFX` & `ValidatorFX` : Pour des formulaires robustes et validés.
    *   `BootstrapFX` : Pour le styling CSS inspiré de Bootstrap.
    *   `TilesFX` : Pour les tuiles de tableau de bord.
    *   `Jackson` & `org.json` : Pour le traitement JSON.

## Structure de la Base de Données

L'application utilise une table principale `job_descriptions` pour stocker toutes les informations relatives aux offres d'emploi.

### Table : `job_descriptions`

| Colonne | Type | Description |
| :--- | :--- | :--- |
| `JOB_ID` | `BIGINT` | Identifiant unique de l'offre (Clé Primaire) |
| `Job_Title` | `VARCHAR` | Intitulé du poste |
| `Company` | `VARCHAR` | Nom de l'entreprise |
| `Location` | `VARCHAR` | Ville ou région |
| `Country` | `VARCHAR` | Pays |
| `Work_Type` | `VARCHAR` | Type de contrat (Full-time, Contract, etc.) |
| `Salary_Range` | `VARCHAR` | Fourchette salariale |
| `Job_Posting_Date` | `DATE` | Date de publication |
| `Experience` | `VARCHAR` | Expérience requise |
| `Qualifications` | `VARCHAR` | Qualifications nécessaires |
| `Job_Description` | `TEXT` | Description détaillée du poste |
| `skills` | `TEXT` | Compétences requises |
| `Responsibilities` | `TEXT` | Responsabilités du poste |
| `Company_Profile` | `JSON` | Détails sur l'entreprise (Secteur, CEO, Site web...) |
| `...` | `...` | *Autres champs (Contact, Benefits, etc.)* |

## Prérequis

Avant de commencer, assurez-vous d'avoir installé :

*   [JDK 23](https://jdk.java.net/23/)
*   [Maven](https://maven.apache.org/)
*   [MySQL Server](https://dev.mysql.com/downloads/mysql/)

## Installation et Configuration

1.  **Cloner le dépôt**
    ```bash
    git clone https://github.com/nikxo/Tyrell.git
    cd Tyrell
    ```

2.  **Configuration de la Base de Données**
    *   Créez une base de données MySQL nommée `job_app`.
    *   Assurez-vous que votre serveur MySQL est en cours d'exécution sur le port `3306`.
    *   *Note : Les identifiants par défaut sont configurés dans `src/main/java/app/tyrell/backend/Database.java`. Vous devrez peut-être les modifier pour correspondre à votre configuration locale :*
        ```java
        this.user = "App";      // Votre utilisateur MySQL
        this.password = "jobteaser"; // Votre mot de passe
        ```

3.  **Compiler le projet**
    ```bash
    mvn clean install
    ```

## Utilisation

Pour lancer l'application, vous pouvez utiliser Maven ou votre IDE préféré (IntelliJ IDEA, Eclipse).

**Via Maven :**
```bash
mvn javafx:run
```
*(Assurez-vous que la configuration `mainClass` dans votre `pom.xml` pointe bien vers `app.tyrell.controller.TyrellApplication`)*

## Structure du Projet

```
Tyrell/
├── src/
│   ├── main/
│   │   ├── java/app/tyrell/
│   │   │   ├── backend/    # Logique métier et accès aux données
│   │   │   └── controller/ # Contrôleurs JavaFX
│   │   └── resources/
│   │       ├── app/        # Fichiers FXML
│   │       ├── css/        # Feuilles de style
│   │       └── Icon/       # Images et icônes
└── pom.xml                 # Configuration Maven
```