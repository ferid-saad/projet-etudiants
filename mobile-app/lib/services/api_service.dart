import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';
import 'package:flutter/foundation.dart';
class ApiService {
  // ⚠️  Adaptez l'URL selon votre environnement :
  //   • Émulateur Android   → http://10.0.2.2:8080
  //   • Simulateur iOS      → http://localhost:8080
  //   • Appareil physique   → http://<IP-de-votre-machine>:8080
  //static const String baseUrl = 'http://localhost:8080/api';
static const String baseUrl = 'http://localhost:8081';
  /// Récupère la liste de tous les étudiants depuis l'API.
  static Future<List<Etudiant>> fetchEtudiants() async {
    final uri = Uri.parse('$baseUrl/api/etudiants');

    final response = await http.get(uri).timeout(
      const Duration(seconds: 20),
      onTimeout: () => throw Exception('Délai de connexion dépassé'),
    );

    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data
          .map((item) => Etudiant.fromJson(item as Map<String, dynamic>))
          .toList();
    } else {
      throw Exception(
        'Erreur serveur (code HTTP : ${response.statusCode})',
      );
    }
  }
}
