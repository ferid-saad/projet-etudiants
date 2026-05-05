// app/etudiants/FormEtudiant.tsx
"use client";
import { useState } from "react";
import axios from "axios";

export default function FormEtudiant({ onAdded }: { onAdded: (e: any) => void }) {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault();
        const res = await axios.post("http://localhost:8888/api/etudiants", { name, email });
        onAdded(res.data);
        setName(""); setEmail("");
    }

    return (
        <form onSubmit={handleSubmit} className="mt-4 space-y-2">
            <input className="border p-2 w-full" placeholder="Nom" value={name} onChange={e => setName(e.target.value)} />
            <input className="border p-2 w-full" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
            <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700">Ajouter</button>
        </form>
    );
}
