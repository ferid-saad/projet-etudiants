import 'package:flutter/material.dart';
import 'screens/etudiant_list_screen.dart';
import 'package:google_fonts/google_fonts.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Gestion des Étudiants',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color.fromARGB(255, 181, 63, 63)),
        useMaterial3: true,
        textTheme: GoogleFonts.robotoTextTheme(), // 👈 ajout ici
      ),
      home: const EtudiantListScreen(),
    );
  }
}
