// app/etudiants/page.tsx
"use client"; // car on veut aussi gérer la suppression
import { useEffect, useState } from "react";
import axios from "axios";
import FormEtudiant from "./FormEtudiant";

export default function EtudiantsPage() {
    const [etudiants, setEtudiants] = useState<any[]>([]);

    useEffect(() => {
        axios.get("http://localhost:8888/api/etudiants").then(res => setEtudiants(res.data));
    }, []);

    async function supprimer(id: number) {
        await axios.delete(`http://localhost:8888/api/etudiants/${id}`);
        setEtudiants(etudiants.filter(e => e.id !== id));
    }

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Étudiants</h1>
            <ul className="space-y-2">
                {etudiants.map(e => (
                    <li key={e.id} className="border p-2 rounded flex justify-between">
                        <span>{e.name} - {e.email}</span>
                        <button onClick={() => supprimer(e.id)} className="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-700">
                            Supprimer
                        </button>
                    </li>
                ))}
            </ul>
            <FormEtudiant onAdded={(newE: any) => setEtudiants([...etudiants, newE])} />
        </div>
    );
}
