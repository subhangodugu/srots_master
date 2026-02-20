import React, { useState } from "react";
import { PremiumService } from "../services/premiumService";

const plans = [
    { months: 4, price: 999 },
    { months: 6, price: 1399 },
    { months: 12, price: 2499 }
];

const PremiumPage: React.FC = () => {
    const [selected, setSelected] = useState<number | null>(null);
    const [utr, setUtr] = useState("");

    const subscribe = async () => {
        if (!selected) return alert("Select a plan");
        if (!utr.trim()) return alert("Enter valid UTR after payment");
        if (utr.trim().length < 10) return alert("Invalid UTR. Please enter a valid 12-digit UTR.");

        try {
            await PremiumService.subscribe(selected, utr.trim());
            alert("Premium activated!");
        } catch (err) {
            alert("Activation failed. Please try again.");
            console.error(err);
        }
    };

    return (
        <div className="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8 flex flex-col items-center">
            <div className="max-w-4xl w-full space-y-8">
                <div className="text-center">
                    <h2 className="text-3xl font-extrabold text-gray-900">Upgrade to Premium</h2>
                    <p className="mt-2 text-lg text-gray-600">Unlock exclusive features and boost your career.</p>
                </div>

                <div className="bg-white shadow rounded-lg p-6 flex flex-col items-center">
                    <h3 className="text-xl font-bold mb-4">Scan & Pay via UPI</h3>
                    <div className="border-4 border-gray-200 rounded-lg p-2 mb-2">
                        <img src="/qr-payment.png" alt="UPI QR" width={220} height={220} />
                    </div>
                    <p className="font-mono text-sm bg-gray-100 px-3 py-1 rounded">UPI: subhanggodugu91@ybl</p>

                    <div className="mt-6 w-full max-w-xs">
                        <input
                            type="text"
                            placeholder="Enter UTR after payment"
                            value={utr}
                            onChange={(e) => setUtr(e.target.value)}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
                        />
                    </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {plans.map(p => (
                        <div
                            key={p.months}
                            className={`cursor-pointer border-2 rounded-xl p-6 transition-all transform hover:scale-105 ${selected === p.months
                                ? "border-blue-500 bg-blue-50 shadow-lg"
                                : "border-gray-200 bg-white hover:border-blue-300"
                                }`}
                            onClick={() => setSelected(p.months)}
                        >
                            <h3 className="text-2xl font-bold text-gray-800">{p.months} Months</h3>
                            <p className="text-3xl font-bold text-blue-600 mt-2">₹{p.price}</p>
                            <p className="text-gray-500 mt-1">
                                {Math.round(p.price / p.months)} / month
                            </p>
                        </div>
                    ))}
                </div>

                <div className="flex justify-center mt-8">
                    <button
                        onClick={subscribe}
                        disabled={!selected || !utr}
                        className={`px-8 py-3 rounded-full text-white font-bold text-lg shadow-md transition-colors ${selected && utr
                            ? "bg-blue-600 hover:bg-blue-700"
                            : "bg-gray-400 cursor-not-allowed"
                            }`}
                    >
                        Activate Premium
                    </button>
                </div>
            </div>
        </div>
    );
};

export default PremiumPage;
