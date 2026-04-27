class Etudiant {
  final int id;
  final String cin;
  final String nom;
  final String dateNaissance;

  Etudiant({
    required this.id,
    required this.cin,
    required this.nom,
    required this.dateNaissance,
  });

  /// Parse un objet JSON reçu de l'API Spring Boot.
  /// Jackson est configuré pour sérialiser LocalDate en "YYYY-MM-DD".
  factory Etudiant.fromJson(Map<String, dynamic> json) {
    return Etudiant(
      id: json['id'] as int,
      cin: json['cin'] as String? ?? '',
      nom: json['nom'] as String? ?? '',
      dateNaissance: json['dateNaissance'] as String? ?? '',
    );
  }

  @override
  String toString() => 'Etudiant(id: $id, cin: $cin, nom: $nom)';
}
