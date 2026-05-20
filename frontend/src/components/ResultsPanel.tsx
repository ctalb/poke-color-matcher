import type {FlossColorMatch, MatchResult} from "../types.ts";

const BASE_URL = "http://localhost:8080";

interface resultsProps {
    matchResult: MatchResult | null;
    isError: boolean;
}

function ColorMatchCard({ flossColorMatch  }: {flossColorMatch: FlossColorMatch}) {
    const [red, green, blue] = flossColorMatch.extractedColor;
    const backgroundColor = `rgba(${red}, ${green}, ${blue})`;

    return (
        <>
            <div style={{
                backgroundColor,
                width: "20px",
                height: "20px",
            }}
            ></div>
            <p>Floss Number: {flossColorMatch.match.number}</p>
            <p>Name: {flossColorMatch.match.name}</p>
        </>
    );
}

export default function ResultsPanel({ matchResult, isError } : resultsProps) {

    return (
        <div className="card">
            <div className="card-body">

                {matchResult &&
                    <img src={`${BASE_URL}/${matchResult.imagePath}`} alt={matchResult.name}/>}

                <h3 className="card-title">
                    {isError && "Pokémon does not exist! Please check your spelling and try again."}
                </h3>

                <h2 className="card-title">{matchResult && matchResult.name}</h2>

                <ul>
                    {matchResult && matchResult.matches.map(match =>
                        <li key={match.extractedColor.join()}>
                            <ColorMatchCard flossColorMatch={match}/>
                        </li>
                    )}
                </ul>
            </div>
        </div>
    );
}