import sys
import os
import copy
import ruamel.yaml as yaml


def generate_workflows(
    template_dir: str,
    conf_file: str,
    workflow_dir: str,
    project_dir: str,
):
    models = []
    for model_dir in os.listdir(project_dir):
        makefile_path = os.path.join(project_dir, model_dir, "Makefile")
        if not os.path.exists(makefile_path) or 'make-template' in makefile_path:
            continue
        models.append(model_dir)
    with open(conf_file, "r") as f:
        conf = yaml.safe_load(f)
    for yml_name in os.listdir(template_dir):
        if yml_name.endswith(".gitkeep"):
            continue
        with open(os.path.join(template_dir, yml_name), "r") as f:
            text = f.read()
            template_file = yaml.round_trip_load(text)
        jobs = template_file.pop("jobs")
        template_file["jobs"] = {}
        for job_name, job_temp in jobs.items():
            for model_name in models:
                job = copy.deepcopy(job_temp)
                job_image = f"{conf['docker']['registry']}/{conf['docker']['owner']}/{model_name}-env:{conf['docker']['tag']}"
                for step in job["steps"]:
                    if "run" not in step:
                        continue
                    step["run"] = step["run"].format(
                        model_name=model_name, 
                        docker_image=job_image,
                    )
                if "container" in job:
                    job["container"]["image"] = job_image
                template_file["jobs"][f"{model_name}-{job_name}"] = job
        with open(os.path.join(workflow_dir, yml_name), "w") as f:
            f.write(yaml.round_trip_dump(template_file))


if __name__ == '__main__':
    generate_workflows(
        sys.argv[1],
        sys.argv[2],
        sys.argv[3],
        sys.argv[4],
    )
