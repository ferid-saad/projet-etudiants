"use client";
import { useEffect, useState } from "react";
import axios from "axios";

export default function DepartementsPage() {
    const [departements, setDepartements] = useState<any[]>([]);

    useEffect(() => {
        axios.get("http://localhost:8888/etudiants-api/api/departements")
            .then(res => setDepartements(res.data));
    }, []);

    return (
        <div>
            <h1 className="text-xl font-bold mb-4">Départements</h1>
            <ul className="space-y-2">
                {departements.map(d => (
                    <li key={d.id} className="p-2 bg-white shadow">
                        {d.nom}
                    </li>
                ))}
            </ul>
        </div>
    );
}
