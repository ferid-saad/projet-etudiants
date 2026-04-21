# Projet Gestion des Étudiants

> **Formateur :** Wahid Hamdi  
> **Technologies :** Spring Boot 4 · PostgreSQL · Docker · Flutter

Mini-projet complet composé d'une API REST, d'une base de données conteneurisée et d'une application mobile.

---

# Lien GitHub

https://hub.docker.com/r/feridsaad/projet-etudiants-api

---

## Structure du projet

```
## 📂 Structure du projet

projet-etudiants/
├── api-spring-boot/        <- API Spring Boot (backend)
│   ├── src/
│   │   ├── main/java/com/example/api/
│   │   │   ├── EtudiantsApiApplication.java   <- Point d’entrée Spring Boot
│   │   │   ├── DataInitializer.java           <- Initialisation des données
│   │   │   ├── entity/                        <- Entités JPA (Etudiant, Departement)
│   │   │   ├── repository/                    <- Interfaces JpaRepository
│   │   │   ├── service/                       <- Logique métier
│   │   │   ├── controller/                    <- Endpoints REST
│   │   │   ├── dto/                           <- Objets de transfert (DTO)
│   │   │   ├── mapper/                        <- Conversion Entity ↔ DTO
│   │   │   ├── exception/                     <- Gestion des erreurs
│   │   │   ├── config/                        <- Configurations (Jackson, OpenAPI)
│   │   ├── main/resources/
│   │   │   ├── application.properties         <- Configuration Spring Boot
│   │   │   ├── static/index.html              <- Page statique
│   │   ├── test/java/com/example/api/
│   │   │   ├── EtudiantsApiApplicationTests.java <- Tests unitaires
│   │   │   ├── EtudiantSteps.java             <- Steps Cucumber (BDD)
│   │   ├── test/resources/features/           <- Scénarios Gherkin
│   ├── Dockerfile                             <- Image Docker du backend
│   ├── pom.xml                                <- Dépendances Maven
│   ├── target/                                <- Fichiers compilés
│
├── mobile-app/             <- Application Flutter (frontend mobile)
│   ├── lib/
│   │   ├── main.dart                          <- Point d’entrée Flutter
│   │   ├── models/etudiant.dart               <- Modèle étudiant
│   │   ├── services/api_service.dart          <- Appels API vers backend
│   │   ├── screens/etudiant_list_screen.dart  <- Écran liste des étudiants
│   ├── pubspec.yaml                           <- Dépendances Flutter
│
├── docker-compose.yml      <- Orchestration API + PostgreSQL
├── K8s/                    <- Déploiement Kubernetes (API + PostgreSQL)
├── README.md               <- Documentation du projet

```

---

## Partie 1 — API REST Spring Boot 4

### Endpoint

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/etudiants` | Retourne la liste de tous les étudiants (JSON) |

### Entité Etudiant

| Champ | Type | Description |
|-------|------|-------------|
| `id` | Long | Identifiant auto-généré |
| `cin` | String | Carte d'identité nationale |
| `nom` | String | Nom complet |
| `dateNaissance` | LocalDate | Date de naissance (ISO-8601) |

### Lancer l'API localement (sans Docker)

Prérequis : Java 21+, Maven 3.9+, PostgreSQL 16 en cours d'exécution sur le port 5432.

1. Créer la base de données :
   ```sql
   CREATE DATABASE etudiants_db;
   ```

2. Démarrer l'application :
   ```bash
   cd api-spring-boot
   mvn spring-boot:run
   ```

3. Tester dans le navigateur ou avec curl :
   ```
   http://localhost:8080/api/etudiants
   ```

---

## Partie 2 — Docker (API + Base de données)

### Prérequis

- Docker Desktop installé et démarré

### Lancer le projet complet

Depuis la **racine** du projet (`projet-etudiants/`) :

```bash
docker compose up --build
```

L'API est ensuite accessible à :

```
http://localhost:8080/api/etudiants
```

### Arrêter les conteneurs

```bash
docker compose down
```

Pour supprimer aussi le volume de données PostgreSQL :

```bash
docker compose down -v
```

### Architecture Docker

```
┌─────────────────────────────────┐  réseau etudiants-network
│  spring-etudiants-api :8080     │◄──────────────────────────►  Votre machine
│  (Spring Boot 4 / Java 21)      │
└────────────────┬────────────────┘
                 │ jdbc:postgresql://db:5432
┌────────────────▼────────────────┐
│  postgres-etudiants :5432       │
│  (PostgreSQL 16)                │
└─────────────────────────────────┘
```

---

## Partie 4 — Déploiement Kubernetes

### Prérequis

- Kubernetes cluster (Minikube, Kind, ou cluster cloud)
- kubectl installé et configuré

### Déployer sur Kubernetes

Depuis le dossier `K8s/` :

1. Appliquer les déploiements :
   ```bash
   kubectl apply -f postgres-deployment.yaml
   kubectl apply -f etudiant-deployment.yaml
   ```

2. Vérifier les pods :
   ```bash
   kubectl get pods
   ```

3. Vérifier les services :
   ```bash
   kubectl get services
   ```

L'API sera accessible via NodePort sur le port 30080 de vos nœuds Kubernetes.

### Architecture Kubernetes

```
┌─────────────────────────────────┐
│  etudiant-deployment            │
│  (Spring Boot API)              │
│  Service: etudiant-service      │
│  NodePort: 30080                │
└────────────────┬────────────────┘
                 │ jdbc:postgresql://postgres-service:5432
┌────────────────▼────────────────┐
│  postgres-deployment            │
│  (PostgreSQL 16)                │
│  Service: postgres-service      │
└─────────────────────────────────┘
```

---

## Partie 3 — Application mobile Flutter

### Prérequis

- Flutter SDK 3.x installé (`flutter --version`)
- Un émulateur Android/iOS lancé **ou** un appareil physique connecté

### Installation

```bash
cd mobile-app
flutter pub get
```

### Configuration de l'URL de l'API

Modifier la constante `_baseUrl` dans `lib/services/api_service.dart` :

| Environnement | URL à utiliser |
|---------------|---------------|
| Émulateur Android | `http://10.0.2.2:8080` (valeur par défaut) |
| Simulateur iOS | `http://localhost:8080` |
| Appareil physique | `http://<IP-de-votre-machine>:8080` |

Pour connaître votre IP locale sur Windows :
```powershell
ipconfig
```

### Lancer l'application

```bash
flutter run
```

### Note : créer le projet Flutter from scratch

Si vous partez de zéro, exécutez d'abord :

```bash
flutter create mobile-app
```

Puis copiez/remplacez :
- `mobile-app/pubspec.yaml`
- `mobile-app/lib/` (tous les fichiers)

---

## Données initiales

5 étudiants sont insérés automatiquement au démarrage (via `DataInitializer`) si la table est vide :

| CIN | Nom | Date de naissance |
|-----|-----|-------------------|
| AB123456 | Ahmed Benali | 2001-03-15 |
| CD789012 | Fatima Zahra Alami | 2002-07-22 |
| EF345678 | Mohamed Chakir | 2000-11-08 |
| GH901234 | Sara Mansouri | 2003-01-30 |
| IJ567890 | Youssef Kadiri | 2001-09-14 |

---

## Stack technique

| Composant | Technologie |
|-----------|------------|
| API REST | Spring Boot 4.0.4 · Java 21 |
| Persistance | Spring Data JPA · Hibernate 6 |
| Base de données | PostgreSQL 16 |
| Conteneurs | Docker · Docker Compose |
| Application mobile | Flutter 3.x · package `http` |

---

## Partie 4 — Déploiement Kubernetes

### Prérequis

- Kubernetes cluster (Minikube, Kind, ou cluster cloud)
- kubectl installé et configuré

### Déployer sur Kubernetes

Depuis le dossier `K8s/` :

1. Appliquer les déploiements :
   ```bash
   kubectl apply -f postgres-deployment.yaml
   kubectl apply -f etudiant-deployment.yaml
   ```

2. Vérifier les pods :
   ```bash
   kubectl get pods
   ```

3. Vérifier les services :
   ```bash
   kubectl get services
   ```

L'API sera accessible via NodePort sur le port 30080 de vos nœuds Kubernetes.

### Architecture Kubernetes

```
┌─────────────────────────────────┐
│  etudiant-deployment            │
│  (Spring Boot API)              │
│  Service: etudiant-service      │
│  NodePort: 30080                │
└────────────────┬────────────────┘
                 │ jdbc:postgresql://postgres-service:5432
┌────────────────▼────────────────┐
│  postgres-deployment            │
│  (PostgreSQL 16)                │
│  Service: postgres-service      │
└─────────────────────────────────┘
```
