# Generated by Django 4.1.7 on 2023-05-13 19:43

from django.db import migrations, models
import uuid


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Recipe',
            fields=[
                ('id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('title', models.TextField(blank=True, null=True)),
                ('description', models.TextField(blank=True, null=True)),
                ('preparation_time', models.PositiveIntegerField(default=0)),
                ('calories', models.PositiveIntegerField(default=0)),
                ('ingredients', models.TextField(blank=True, null=True)),
                ('image', models.TextField(blank=True, null=True)),
                ('category', models.TextField(blank=True, null=True)),
                ('recipe', models.TextField(blank=True, null=True)),
            ],
        ),
    ]
