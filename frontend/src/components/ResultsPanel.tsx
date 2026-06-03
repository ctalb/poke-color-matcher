import type {MatchResult} from "../types.ts";
import BASE_URL from "../config.ts";

interface ResultsProps {
    matchResult: MatchResult | null;
    isError: boolean;
}

export default function ResultsPanel({ matchResult, isError } : ResultsProps) {

    return (
        <div className="card">
            <div className="card-body">
                <div className="text-center">
                    <h3 className="card-title">
                        {isError && "Pokémon does not exist! Please check your spelling and try again."}
                    </h3>

                    {matchResult &&
                        <img src={`${BASE_URL}/${matchResult.imagePath}`} alt={matchResult.name}/>}

                    <h2 className="card-title">{matchResult &&
                        matchResult.name.charAt(0).toUpperCase() + matchResult.name.slice(1)}
                    </h2>
                </div>

                {matchResult &&
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Color</th>
                            <th scope="col">Floss Number</th>
                            <th scope="col">Floss Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {matchResult.matches.map(result => {
                            const [red, green, blue] = result.extractedColor;
                            const backgroundColor = `rgb(${red}, ${green}, ${blue})`;

                            return (
                                <tr key={result.extractedColor.join()}>
                                    <td>
                                        <div style={{
                                            backgroundColor,
                                            width: "20px",
                                            height: "20px",
                                        }}
                                        ></div>
                                    </td>
                                    <td>{result.match.number}</td>
                                    <td>{result.match.name}</td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>}
            </div>
        </div>
    );
}