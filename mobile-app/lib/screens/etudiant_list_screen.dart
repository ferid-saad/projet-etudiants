import 'package:flutter/material.dart';
import '../models/etudiant.dart';
import '../services/api_service.dart';

class EtudiantListScreen extends StatefulWidget {
  const EtudiantListScreen({super.key});

  @override
  State<EtudiantListScreen> createState() => _EtudiantListScreenState();
}

class _EtudiantListScreenState extends State<EtudiantListScreen> {
  late Future<List<Etudiant>> _futureEtudiants;

  @override
  void initState() {
    super.initState();
    _futureEtudiants = ApiService.fetchEtudiants();
  }

  void _refresh() {
    setState(() {
      _futureEtudiants = ApiService.fetchEtudiants();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Liste des Étudiants'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            tooltip: 'Actualiser',
            onPressed: _refresh,
          ),
        ],
      ),
      body: FutureBuilder<List<Etudiant>>(
        future: _futureEtudiants,
        builder: (context, snapshot) {
          // ── Chargement ────────────────────────────────────────────────────
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          // ── Erreur ────────────────────────────────────────────────────────
          if (snapshot.hasError) {
            return Center(
              child: Padding(
                padding: const EdgeInsets.all(24),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Icon(Icons.error_outline, size: 64, color: Colors.red),
                    const SizedBox(height: 16),
                    Text(
                      snapshot.error.toString(),
                      textAlign: TextAlign.center,
                      style: const TextStyle(color: Colors.red),
                    ),
                    const SizedBox(height: 24),
                    ElevatedButton.icon(
                      onPressed: _refresh,
                      icon: const Icon(Icons.refresh),
                      label: const Text('Réessayer'),
                    ),
                  ],
                ),
              ),
            );
          }

          // ── Liste vide ────────────────────────────────────────────────────
          if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('Aucun étudiant trouvé.'));
          }

          // ── Affichage de la liste ─────────────────────────────────────────
          final etudiants = snapshot.data!;
          return ListView.builder(
            padding: const EdgeInsets.all(8),
            itemCount: etudiants.length,
            itemBuilder: (context, index) {
              final e = etudiants[index];
              return Card(
                margin: const EdgeInsets.symmetric(vertical: 6, horizontal: 4),
                elevation: 3,
                child: ListTile(
                  contentPadding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 8,
                  ),
                  leading: CircleAvatar(
                    backgroundColor: Theme.of(context).colorScheme.primary,
                    child: Text(
                      e.nom.isNotEmpty ? e.nom[0].toUpperCase() : '?',
                      style: const TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  title: Text(
                    e.nom,
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const SizedBox(height: 4),
                      Row(children: [
                        const Icon(Icons.badge, size: 14, color: Colors.grey),
                        const SizedBox(width: 6),
                        Text('CIN : ${e.cin}'),
                      ]),
                      const SizedBox(height: 2),
                      Row(children: [
                        const Icon(Icons.cake, size: 14, color: Colors.grey),
                        const SizedBox(width: 6),
                        Text('Date de naissance : ${e.dateNaissance}'),
                      ]),
                    ],
                  ),
                  isThreeLine: true,
                ),
              );
            },
          );
        },
      ),
    );
  }
}
